/**
 * @author Louis LeBlanc
 * For Homework Assignment 2
 * Expert Software Development in Java
 * Brandeis University
 * Instructed by Vitaly Yurik
 */
package edu.brandeis.rseg105.mvc.web;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;

import edu.brandeis.rseg105.mvc.domain.Book;
import edu.brandeis.rseg105.mvc.services.BookService;
import edu.brandeis.rseg105.mvc.util.BookGrid;
import edu.brandeis.rseg105.mvc.util.Message;
import edu.brandeis.rseg105.mvc.util.UrlUtil;

@RequestMapping("/books")
@Controller
public class BookController {
	private final Logger logger = LoggerFactory.getLogger(BookController.class);

	private BookService bookService;
	private MessageSource messageSource;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model uiModel) {
		logger.info("Listing books");

		List<Book> books = bookService.findAll();
		uiModel.addAttribute("books", books);

		logger.info("No. of books: " + books.size());
		/* This is a little verbose, but it tells us what book records are being
		 * returned to the caller
		 */
		books.forEach(book -> {
			logger.info(book.toString());
			logger.info("-----------------");
		});

		return "books/list";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, Model uiModel) {
		Book book = bookService.findById(id);
		uiModel.addAttribute("book", book);

		return "books/show";
	}

	/* First, Spring MVC will try to bind the submitted data to the Book domain
	 * object and perform the type conversion and formatting automatically. If
	 * binding errors are found, the errors will be saved into the
	 * BindingResult interface, and an error message will be saved into the
	 * Model, redisplaying the edit view. If the binding is successful, the
	 * data will be saved, and the logical view name will be returned for the
	 * display book view by using redirect: as the prefix.
	 */
	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.POST)
	public String update(@Valid Book book, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes,
			Locale locale) {
		logger.info("Updating book");
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("message", new Message("error",
				messageSource.getMessage("book_save_fail", new Object[]{}, locale)));
			uiModel.addAttribute("book", book);
			return "books/update";
		}
		uiModel.asMap().clear();
		/* Note that we want to display the message after the redirect, so we
		 * need to use the RedirectAttributes.addFlashAttribute() method for
		 * displaying the success message in the show book view. The Message
		 * class is a custom class that stores the message retrieved from
		 * MessageSource and the type of message (that is, success or error) for
		 * the view to display in the message area
		 */
		redirectAttributes.addFlashAttribute("message", new Message("success",
				messageSource.getMessage("book_save_success", new Object[]{}, locale)));
		bookService.save(book);
		return "redirect:/books/" + UrlUtil.encodeUrlPathSegment(book.getId().toString(), httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		uiModel.addAttribute("book", bookService.findById(id));
		return "books/update";
	}

	//BindingResult used as an object to look for validation errors.
	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid Book book, BindingResult bindingResult, Model uiModel,
				HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes,
				Locale locale) {
		logger.info("Creating book");
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("message", new Message("error",
				messageSource.getMessage("book_save_fail", new Object[]{}, locale)));
			uiModel.addAttribute("book", book);
			return "books/create";
		}
		uiModel.asMap().clear();
		redirectAttributes.addFlashAttribute("message", new Message("success",
			messageSource.getMessage("book_save_success", new Object[]{}, locale)));

		logger.info("Book id: " + book.getId());

		bookService.save(book);
		return "redirect:/books/";
	}

	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String createForm(Model uiModel) {
		Book book = new Book();
		uiModel.addAttribute("book", book);

		return "books/create";
	}

	@ResponseBody
	@RequestMapping(value = "/listgrid", method = RequestMethod.GET, produces="application/json")
	public BookGrid listGrid(@RequestParam(value = "page", required = false) Integer page,
				@RequestParam(value = "rows", required = false) Integer rows,
				@RequestParam(value = "sidx", required = false) String sortBy,
				@RequestParam(value = "sord", required = false) String order) {

		logger.info("Listing books for grid with page: {}, rows: {}", page, rows);
		logger.info("Listing books for grid with sort: {}, order: {}", sortBy, order);

		// Process order by
		Sort sort = null;
		String orderBy = sortBy;

		if (orderBy != null && order != null) {
			if (order.equals("desc")) {
				sort = new Sort(Sort.Direction.DESC, orderBy);
			} else
				sort = new Sort(Sort.Direction.ASC, orderBy);
		}

		// Constructs page request for current page
		// Note: page number for Spring Data JPA starts with 0, while jqGrid starts with 1
		PageRequest pageRequest = null;

		if (sort != null) {
			pageRequest = PageRequest.of(page - 1, rows, sort);
		} else {
			pageRequest = PageRequest.of(page - 1, rows);
		}

		Page<Book> bookPage = bookService.findAllByPage(pageRequest);

		/* Construct the grid data that will return as JSON data */
		BookGrid bookGrid = new BookGrid();

		bookGrid.setCurrentPage(bookPage.getNumber() + 1);
		bookGrid.setTotalPages(bookPage.getTotalPages());
		bookGrid.setTotalRecords(bookPage.getTotalElements());

		bookGrid.setBookData(Lists.newArrayList(bookPage.iterator()));

		/* This is a little less verbose.  It will log only the records being
		 * returned for a specific page request.
		 * This includes requesting a subsequent page in our display as well as
		 * re-sorting by a specific field.
		 */
		bookGrid.getBookData().forEach(book -> {
			logger.info(book.toString());
			logger.info("-----------------");
		});

		return bookGrid;
	}

	@Autowired
	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}

	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
