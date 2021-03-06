package edu.brandeis.rseg105.mvc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import edu.brandeis.rseg105.mvc.domain.Book;
import edu.brandeis.rseg105.mvc.repos.BookRepository;

@Transactional
@Service("bookService")
public class BookServiceImpl implements BookService {

	private BookRepository bookRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Book> findAll() {
		return Lists.newArrayList(bookRepository.findAll());
	}

	@Override
	@Transactional(readOnly = true)
	public Book findById(Long id) {
		return bookRepository.findById(id).get();
	}

	@Override
	public Book save(Book book) {
		return bookRepository.save(book);
	}

	@Autowired
	public void setBookRepository(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Book> findAllByPage(Pageable pageable) {
		return bookRepository.findAll(pageable);
	}
}
