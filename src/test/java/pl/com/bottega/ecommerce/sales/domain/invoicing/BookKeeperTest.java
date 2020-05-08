package pl.com.bottega.ecommerce.sales.domain.invoicing;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.Product;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class BookKeeperTest {

    @Mock private Id id;
    @Mock private Money money;

    @Mock private ClientData client;
    @Mock private TaxPolicy taxPolicy;

    private RequestItem requestItem;
    private InvoiceRequest invoiceRequest;
    private BookKeeper bookKeeper;

    @Before
    public void setUp() throws Exception {
        this.bookKeeper = new BookKeeper(new InvoiceFactory());
        this.invoiceRequest = new InvoiceRequest(this.client);

        this.requestItem = new RequestItem(
                new Product(this.id, this.money, "", ProductType.STANDARD).generateSnapshot()
                , 0
                , Money.ZERO
        );

        Mockito.when(taxPolicy.calculateTax(ProductType.STANDARD, Money.ZERO))
                .thenReturn(new Tax(Money.ZERO, ""));
    }

    @Test
    public void testIfOneInvoice() {
        this.invoiceRequest.add(this.requestItem);
        assertEquals(1, bookKeeper.issuance(invoiceRequest, taxPolicy).getItems().size());
    }

}