package automattedbillingsoftware_BL;

import automatedbillingsoftware_DA.Products_DA;
import automatedbillingsoftware_modal.Products;
import java.util.List;

/**
 *
 * @author Arka
 */
public class ProductsBL {

    private Products_DA productsda;

    public ProductsBL() {
        if (productsda == null) {
            productsda = new Products_DA();
        }
    }

    public void deleteProduct(int id) {
        productsda.deleteProducts(id);
    }

    public ProductsBL(Products_DA productsda) {
        this.productsda = productsda;
    }

    public Products addProducts(Products prod) {
        return productsda.addProducts(prod);
    }

    public List<Products> fetchProdList() {
        return productsda.fetchAllProducts();
    }

    public Products fetchProdById(int id) {
        return productsda.fetchProductById(id);
    }

    public List<Products> fetchProductsforSearch(String prodName, String catName, double minQty, double maxQty, double minPrice, double maxPrice) {

        return productsda.fetchProductSearchList(catName, prodName, minQty, maxQty, minPrice, maxPrice);
    }

    public void updateProduct(Products prod) {
        productsda.updateProduct(prod);
    }

    public List<Products> fetchProductByQRCode(String qrcode) {
        return productsda.fetchProductByQRCode(qrcode);
    }

    public Products fetchProductByName(String name) {
        return productsda.fetchProductByName(name);
    }
}
