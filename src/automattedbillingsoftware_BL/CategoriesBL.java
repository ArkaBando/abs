/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automattedbillingsoftware_BL;

import automatedbillingsoftware_DA.Categories_DA;
import automatedbillingsoftware_modal.Categories;
import automatedbillingsoftware_modal.Products;
import java.util.List;

/**
 *
 * @author Arka
 */
public class CategoriesBL {

    private static Categories_DA categories;

    public CategoriesBL() {
        if (null == categories) {
            CategoriesBL.categories = new Categories_DA();
        }
    }

    public Categories addCategories(Categories cat) {
        return categories.addCategories(cat);
    }

    public List<Categories> fetchCategoriesList(String keyword) {
        return categories.searchCategories(keyword);
    }

    public List<Categories> fetchCategoriesesList() {
        return categories.fetchCategorieses();
    }

    public void deleteCategory(Categories cat) {
        categories.deleteCategory(cat);
    }

 
    
    public Categories fetchCategoriesbyId(int id) {
        return categories.fetchCategoryById(id);
    }

    public void updateCategories(Categories cat) {
        categories.updateCategory(cat);
    }

    public Categories fetchCategoriesByName(String name) {
        return categories.fetchCategoryByName(name);
    }
}
