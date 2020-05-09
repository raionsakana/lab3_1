package pl.com.bottega.ecommerce.sales.domain.invoicing;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import java.util.ArrayList;
import java.util.List;

public class InvoiceRequestBuilder {

    private ClientData client;
    private RequestItem sampleRequest;
    private int items;

    public InvoiceRequestBuilder withClient(ClientData client) {
        this.client = client;
        return this;
    }

    public InvoiceRequestBuilder withSampleRequest(RequestItem requestItem) {
        this.sampleRequest = requestItem;
        return this;
    }

    public InvoiceRequestBuilder withItems(int items) {
        this.items = items;
        return this;
    }

    public InvoiceRequest build() {
        InvoiceRequest invoiceRequest = new InvoiceRequest(this.client);

        for (int i = 0; i < this.items; i++) {
            invoiceRequest.add(this.sampleRequest);
        }

        return invoiceRequest;
    }

}
