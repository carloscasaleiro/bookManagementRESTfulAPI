package CC.controller;

import CC.model.Book;
import CC.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/books")
@RestController
public class BookController {

    private BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    //curl http://localhost:5000/books

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }
    //curl -X POST -H "Content-Type: application/json" -d '{
    //    "title": "The New Book",
    //    "author": "John Doe",
    //    "year_published": 2023,
    //    "isbn": "978-1234567890",
    //    "price": 19.99
    //}' http://localhost:5000/books

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {

        Optional<Book> existingBookOptional = bookRepository.findById(id);

        if (existingBookOptional.isPresent()) {
            Book existingBook = existingBookOptional.get();

            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setYearPublished(updatedBook.getYearPublished());
            existingBook.setIsbn(updatedBook.getIsbn());
            existingBook.setPrice(updatedBook.getPrice());

            Book savedBook = bookRepository.save(existingBook);

            return new ResponseEntity<>(savedBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //curl -X PUT -H "Content-Type: application/json" -d '{
    //    "title": "Updated Title",
    //    "author": "Updated Author",
    //    "year_published": 2022,
    //    "isbn": "978-1234567890",
    //    "price": 24.99
    //}' http://localhost:5000/books/{id}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {

        if (bookRepository.existsById(id)) {

            bookRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //curl -X DELETE http://localhost:5000/books/{id}

    @GetMapping("/{author}")
    public List<Book> getBooksByAuthor(@PathVariable String author) {
        return bookRepository.findByAuthor(author);
    }
    //curl http://localhost:5000/books/{author}
}
