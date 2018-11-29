package edu.brandeis.rseg105.mvc.repos;

import org.springframework.data.repository.PagingAndSortingRepository;

import edu.brandeis.rseg105.mvc.domain.Book;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> {

}
