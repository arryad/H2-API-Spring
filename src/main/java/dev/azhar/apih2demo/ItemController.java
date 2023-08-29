package dev.azhar.apih2demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemRepository itemRepository;

    @Autowired
    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<String> addItem(@RequestBody Item item) {
        itemRepository.save(item);
        return ResponseEntity.status(HttpStatus.CREATED).body("Item added successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateItem(@PathVariable Long id, @RequestBody Item updatedItem) {
        Item existingItem = itemRepository.findById(id).orElse(null);

        if (existingItem == null) {
            return ResponseEntity.notFound().build(); // Return 404 Not Found response
        }

        existingItem.setName(updatedItem.getName());
        existingItem.setWeight(updatedItem.getWeight());

        itemRepository.save(existingItem);
        return ResponseEntity.ok("Item updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id) {
        Item item = itemRepository.findById(id).orElse(null);

        if (item == null) {
            return ResponseEntity.notFound().build(); // Return 404 Not Found response
        }

        itemRepository.delete(item);
        return ResponseEntity.ok("Item deleted successfully");
    }
}
