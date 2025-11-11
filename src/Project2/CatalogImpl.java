package Program2;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CatalogImpl implements CatalogOperations {
    private final List<Product> products = new ArrayList<>();

    // validate helper
    private void validateProduct(Product p) throws InvalidInputException {
        if (p == null) throw new InvalidInputException("Product cannot be null.");
        if (p.getId() <= 0) throw new InvalidInputException("Product id must be > 0.");
        if (p.getName() == null || p.getName().isEmpty()) throw new InvalidInputException("Product name required.");
        if (p.getPrice() < 0) throw new InvalidInputException("Price must be non-negative.");
        if (p.getStock() < 0) throw new InvalidInputException("Stock must be non-negative.");
    }

    @Override
    public synchronized void addProduct(Product p) throws DuplicateProductException, InvalidInputException {
        validateProduct(p);
        // check duplicate id
        for (Product existing : products) {
            if (existing.getId() == p.getId()) {
                throw new DuplicateProductException("Product with id " + p.getId() + " already exists.");
            }
        }
        products.add(p);
    }

    @Override
    public synchronized void removeProduct(int productId) throws ProductNotFoundException {
        Product toRemove = null;
        for (Product p : products) {
            if (p.getId() == productId) { toRemove = p; break; }
        }
        if (toRemove == null) throw new ProductNotFoundException("Product with id " + productId + " not found.");
        products.remove(toRemove);
    }

    @Override
    public synchronized Product searchById(int productId) throws ProductNotFoundException {
        for (Product p : products) if (p.getId() == productId) return p;
        throw new ProductNotFoundException("Product with id " + productId + " not found.");
    }

    @Override
    public synchronized List<Product> searchByName(String name) {
        if (name == null) return Collections.emptyList();
        String key = name.trim().toLowerCase();
        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p.getName().toLowerCase().contains(key)) result.add(p);
        }
        return result;
    }

    @Override
    public synchronized List<Product> listAll() {
        // return a shallow copy to avoid external modification
        return new ArrayList<>(products);
    }

    // utility sorting helpers (not in interface but used by UI)
    public List<Product> listSortedByPrice(boolean ascending) {
        List<Product> copy = listAll();
        copy.sort(Comparator.comparingDouble(Product::getPrice));
        if (!ascending) Collections.reverse(copy);
        return copy;
    }

    public List<Product> listSortedByName() {
        List<Product> copy = listAll();
        copy.sort(Comparator.comparing(Product::getName, String.CASE_INSENSITIVE_ORDER));
        return copy;
    }
}