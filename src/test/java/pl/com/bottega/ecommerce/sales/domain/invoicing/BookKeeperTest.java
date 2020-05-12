package pl.com.bottega.ecommerce.sales.domain.invoicing;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class BookKeeperTest {

    private TaxPolicy taxPolicy;
    private ProductData productData;
    private RequestItem requestItem;
    private InvoiceRequest invoiceRequest;
    private BookKeeper bookKeeper;

    @Before
    public void setUp() throws Exception {
        this.bookKeeper = new BookKeeper(new InvoiceFactory());

        this.productData = new ProductDataBuilder()
                .withMoney(Money.ZERO)
                .withName("")
                .withType(ProductType.STANDARD)
                .build();

        this.requestItem = new RequestItemBuilder()
                .withProductData(this.productData)
                .withQuantity(0)
                .withMoney(Money.ZERO)
                .build();

        this.taxPolicy = mock(TaxPolicy.class);

        Mockito.when(taxPolicy.calculateTax(ProductType.STANDARD, Money.ZERO))
                .thenReturn(new Tax(Money.ZERO, ""));
    }

    @Test
    public void testIfOneInvoice() {
        this.invoiceRequest = new InvoiceRequestBuilder()
                .withSampleRequest(this.requestItem)
                .withItems(1)
                .build();

        assertEquals(1, this.bookKeeper.issuance(this.invoiceRequest, this.taxPolicy).getItems().size());
    }

    @Test
    public void testIfCalculateTaxInvokedTwice() {
        this.invoiceRequest = new InvoiceRequestBuilder()
                .withSampleRequest(this.requestItem)
                .withItems(2)
                .build();

        this.bookKeeper.issuance(this.invoiceRequest, this.taxPolicy);
        Mockito.verify(this.taxPolicy, Mockito.times(2)).calculateTax(Mockito.any(), Mockito.any());
    }

    @Test
    public void testIfInvoiceIsEmpty() {
        this.invoiceRequest = new InvoiceRequestBuilder().build();

        assertEquals(0, this.bookKeeper.issuance(this.invoiceRequest, this.taxPolicy).getItems().size());
    }

    @Test(expected = NullPointerException.class)
    public void testIfInvoiceThrowNull() {
        this.bookKeeper.issuance(null, this.taxPolicy);
    }

    @Test
    public void testIfInvoiceHandleMultipleElements() {
        this.invoiceRequest = new InvoiceRequestBuilder()
                .withSampleRequest(this.requestItem)
                .withItems(8)
                .build();

        assertEquals(8, this.bookKeeper.issuance(this.invoiceRequest, this.taxPolicy).getItems().size());
    }

    @Test
    public void testIfEmptyInvoiceDoesntCallCalculateTax() {
        this.invoiceRequest = new InvoiceRequestBuilder().build();
        this.bookKeeper.issuance(this.invoiceRequest, this.taxPolicy);
        Mockito.verify(this.taxPolicy, Mockito.times(0)).calculateTax(Mockito.any(), Mockito.any());
    }

    @Test
    public void testIfCalculateTaxHasCorrectParams() {
        this.invoiceRequest = new InvoiceRequestBuilder()
                .withSampleRequest(this.requestItem)
                .withItems(1)
                .build();

        this.bookKeeper.issuance(this.invoiceRequest, this.taxPolicy);
        Mockito.verify(this.taxPolicy, Mockito.times(1)).calculateTax(ProductType.STANDARD, Money.ZERO);
    }

}