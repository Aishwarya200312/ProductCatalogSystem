package Program2;
import java.util.List;
public interface CatalogOperations {
	void addProduct(Product p) throws DuplicateProductException,InvalidInputException;
	void removeProduct(int productId) throws ProductNotFoundException;
	Product searchById(int productId) throws ProductNotFoundException;
	List<Product> searchByName(String name);
	List<Product> listAll();

}