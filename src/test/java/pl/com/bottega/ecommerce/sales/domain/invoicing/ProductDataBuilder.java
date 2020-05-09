package pl.com.bottega.ecommerce.sales.domain.invoicing;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.Product;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

public class ProductDataBuilder {

    private Money price;
    private String name;
    private ProductType type;

    public ProductDataBuilder withMoney(Money money) {
        this.price = money;
        return this;
    }

    public ProductDataBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ProductDataBuilder withType(ProductType type) {
        this.type = type;
        return this;
    }

    public ProductData build() {
        return new Product(Id.generate(), this.price, this.name, this.type).generateSnapshot();
    }

}
