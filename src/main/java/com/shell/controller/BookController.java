package com.shell.controller;

import com.shell.pojo.Books;
import com.shell.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    @Qualifier("BookServiceImpl")
    private BookService bookService;

    @RequestMapping("/allBook")
    public String list(Model model) {
        List<Books> list = bookService.queryAllBook();
        model.addAttribute("list", list);
        return "allBook";
    }


    @RequestMapping("/toAddBook")
    public String toAddPaper() {
        return "addBook";
    }

    //添加书籍的请求
    @RequestMapping("/addBook")
    public String addPaper(Books books) {
//        System.out.println(books);
        bookService.addBook(books);
        return "redirect:/book/allBook";
    }

    //跳转修改页面
    @RequestMapping("/toUpdateBook")
    public String toUpdateBook(int id, Model model) {
        Books books = bookService.queryBookById(id);
        model.addAttribute("book", books);
        return "updateBook";
    }

    //修改书籍的请求
    @RequestMapping("/updateBook")
    public String UpdateBook(Books books) {
//        System.out.println(books);
        bookService.updateBook(books);
        bookService.queryAllBook();
        return "redirect:/book/allBook";
    }

    //删除页面
    @RequestMapping("/del/{bookId}")
    public String DeleteBook(@PathVariable("bookId")  int id) {
        bookService.deleteBookById(id);
//        model.addAttribute("book",books)
        return "redirect:/book/allBook";
    }

    //搜索
    @RequestMapping("/queryBook")
    public String queryBook(String queryBookName,Model model){
        Books books=bookService.queryBookByName(queryBookName);


        List<Books> list = new ArrayList<Books>();
        list.add(books);

        if(books==null){
            list = bookService.queryAllBook();
            model.addAttribute("error","未查到");
        }
        model.addAttribute("list", list);
        return "allBook";}

}

