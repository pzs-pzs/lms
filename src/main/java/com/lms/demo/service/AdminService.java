package com.lms.demo.service;

import com.lms.demo.domain.*;
import com.lms.demo.dto.BookList;
import com.lms.demo.dto.BookStatus;
import com.lms.demo.dto.BorrowHistory;
import com.lms.demo.repository.*;
import com.lms.demo.util.BorrowBookUtil;
import com.lms.demo.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class AdminService {

    @Autowired
    BorrowBookRepository borrowBookRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookInventoryRepository bookInventoryRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    FineRepository fineRepository;

    public Map<String,Object> getBorrowHistory(Integer page, Integer size,String username) {
        Map<String,Object> map = new HashMap<>();
        Sort sort = new Sort(Sort.Direction.DESC,"updateDate");
        PageRequest pageRequest = new PageRequest(page,size,sort);
        User user = userRepository.findByName(username);
        if(user==null){
            map.put("page",page);
            return map;
        }
        Page<BorrowBooksTable> p = borrowBookRepository.findAllByUser(user.getId(),pageRequest);
        List<BorrowHistory> bookList = new ArrayList<>();
        List<BorrowBooksTable> borrowBooksTables = p.getContent();
        for (BorrowBooksTable b : borrowBooksTables) {
            Book book = bookRepository.findOne(1,b.getBookId());
            BorrowHistory borrowHistory = BorrowBookUtil.getBorrowHistory(b,book,user);
            bookList.add(borrowHistory);
        }
        map.put("page",p);
        map.put("bookList",bookList);
        map.put("username",user.getName());
        map.put("userID",user.getId());
        return map;
    }

    public Map<String,Object> getBookStatusList(Integer page,Integer size){
        Map<String,Object> map = new HashMap<>();
        Sort sort = new Sort(Sort.Direction.DESC,"updateDate");
        Pageable pageable = new PageRequest(page,size,sort);
        List<BookInventory> bookInventoryList = bookInventoryRepository.findAll(1,pageable).getContent();
        List<BookStatus> bookStatusList = new ArrayList<>();
        for (BookInventory b : bookInventoryList) {
            Book book = bookRepository.findTopOrOrderByName(b.getBookName());
            BookStatus bookStatus = BorrowBookUtil.getBookStatusList(b,book);
            bookStatusList.add(bookStatus);
        }

        map.put("page",page);
        map.put("bookStatusList",bookStatusList);
        return map;
    }

    public Page<BookInventory> getAllBookStatusList(int page, int size){
        Sort sort = new Sort(Sort.Direction.DESC,"createDate");
        Pageable pageable = new PageRequest(page,size,sort);
        Page<BookInventory> bookInventoryPage = bookInventoryRepository.findAll(1,pageable);
        return bookInventoryPage;
    }

    public Page<BookInventory> getBookStatusPageByType(Integer page, Integer size, String type) {
        Specification<BookInventory> specification = new Specification<BookInventory>() {
            @Override
            public Predicate toPredicate(Root<BookInventory> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<String> t = root.get("bookType");
                Path<Integer> status = root.get("status");
                Predicate p1 = criteriaBuilder.like(t,"%"+type+"%");
                Predicate p2 = criteriaBuilder.equal(status,1);
                return criteriaBuilder.and(p1,p2);
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC,"createDate");
        Pageable pageable = new PageRequest(page,size,sort);
        List<BookInventory>  bookInventoryList = bookInventoryRepository.findAll(specification,pageable).getContent();
        return bookInventoryRepository.findAll(specification,pageable);
    }

    public Map<String,Object> getBookStatusListBytype(List<BookInventory> bookInventoryList){
        Map<String,Object> map = new HashMap<>();
        List<BookStatus> bookStatusList = new ArrayList<>();
        for (BookInventory b : bookInventoryList) {
            Book book = bookRepository.findTopOrOrderByName(b.getBookName());
            BookStatus bookStatus = BorrowBookUtil.getBookStatusList(b,book);
            bookStatusList.add(bookStatus);
        }
        map.put("bookStatusList",bookStatusList);
        return map;
    }

    public String AddAdmin(String username, String password,
                               String email,String num){
        User users = userRepository.findByName(username);
        if(users!=null){
            System.out.println(users.getName());
            return "Username is repeated";
        }
        User user = new User();
        BCryptPasswordEncoder encoder =new BCryptPasswordEncoder(4);
        user.setPassword(encoder.encode(password));
        user.setName(username);
        user.setNum(num);
        user.setEnabled(true);
        user.setEmail(email);
        user.setStatus(1);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        User result = userRepository.save(user);
        long rid = new Long(1);
        UserRole userRole = new UserRole();
        userRole.setUserId(userRepository.findByName(username).getId());
        userRole.setRoleId(rid);
        userRoleRepository.save(userRole);
        if(result!=null){
            return "Add administrator sueecss";
        }else{
            return "Add administrator failed";
        }
    }

    public String deleteUser(Long id){
        User user = userRepository.getOne(id);
        user.setStatus(0);
        userRepository.save(user);
        return "Delete successfully";
    }

    public String startUser(Long id){
        User user = userRepository.getOne(id);
        user.setStatus(1);
        userRepository.save(user);
        return "Start successfully";
    }

    public String AddStudent(String username, String password,
                           String email,String num){
        User users = userRepository.findByName(username);
        if(users!=null){
            return "Username is not available";
        }
        User user = new User();
        BCryptPasswordEncoder encoder =new BCryptPasswordEncoder(4);
        user.setPassword(encoder.encode(password));
        user.setName(username);
        user.setEnabled(true);
        user.setEmail(email);
        user.setNum(num);
        user.setStatus(1);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        User result = userRepository.save(user);
        long rid = new Long(2);
        UserRole userRole = new UserRole();
        userRole.setUserId(userRepository.findByName(username).getId());
        userRole.setRoleId(rid);
        userRoleRepository.save(userRole);
        if(result!=null){
            return "Add student sueecss";
        }else{
            return "Add student failed";
        }
    }

    public Page<Fine> getAllPaidList(int page, int size, int status){
        Sort sort = new Sort(Sort.Direction.DESC,"updateDate");
        PageRequest pageRequest = new PageRequest(page,size,sort);
        return fineRepository.findAllByStatus(status,pageRequest);
    }

    public Page<Fine> getAllPaidListByDateRange(int page, int size, int statu,String DateRange){
        Date date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if(DateRange.equals("year")){
            date = DateUtils.getBeforeYear(new Date());
        }else if(DateRange.equals("month")){
            date = DateUtils.getBeforeMonth(new Date());
        }else {
            date = DateUtils.getBeforeDays(new Date(),7);
        }
        String qdate = dateFormat.format(date);
        System.out.println(date);
        Specification<Fine> specification = new Specification<Fine>() {

            @Override
            public Predicate toPredicate(Root<Fine> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<Date> createDate = root.get("createDate");
                Path<Integer> status = root.get("status");
                Predicate p1 = cb.equal(status,statu);
                Predicate p3 = cb.greaterThan(createDate.as(String.class),qdate);
                return cb.and(p1,p3);
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC,"updateDate");
        PageRequest pageRequest = new PageRequest(page,size,sort);
        return fineRepository.findAll(specification,pageRequest);
    }

    public Page<Fine> getSpecifyPaidList(int page, int size, int status,String userId){
        Sort sort = new Sort(Sort.Direction.DESC,"updateDate");
        PageRequest pageRequest = new PageRequest(page,size,sort);
        return fineRepository.findAllByName(status,userId,pageRequest);
    }

    public Page<BorrowBooksTable> getAllUnpaidList(int page, int size,int statu) {
        Date date = DateUtils.getBeforeDays(new Date(),15);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Specification<BorrowBooksTable> specification = new Specification<BorrowBooksTable>() {
            @Override
            public Predicate toPredicate(Root<BorrowBooksTable> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Date> createDate = root.get("createDate");
                Path<Integer> status = root.get("status");
                Predicate p1 = criteriaBuilder.equal(status,statu);
                Predicate p3 = criteriaBuilder.lessThan(createDate.as(String.class),dateFormat.format(date));
                return criteriaBuilder.and(p1,p3);
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC,"createDate");
        Pageable pageable = new PageRequest(page,size,sort);
        return borrowBookRepository.findAll(specification,pageable);
    }

    public Page<BorrowBooksTable> getSpecifyUnpaidList(int page, int size, int status,String username) {
        User user = userRepository.findByName(username);
        Date date = DateUtils.getBeforeDays(new Date(),15);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Specification<BorrowBooksTable> specification = new Specification<BorrowBooksTable>() {
            @Override
            public Predicate toPredicate(Root<BorrowBooksTable> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Date> createDate = root.get("createDate");
                Path<String> userId = root.get("userId");
                Path<Integer> status = root.get("status");
                Predicate p1 = criteriaBuilder.equal(status,"1");
                Predicate p2 = criteriaBuilder.equal(userId,user.getId());
                Predicate p3 = criteriaBuilder.lessThan(createDate.as(String.class),dateFormat.format(date));
                return criteriaBuilder.and(p1,p3);
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC,"createDate");
        Pageable pageable = new PageRequest(page,size,sort);
        return borrowBookRepository.findAll(specification,pageable);
    }

    public String BorrowBook(Long bookId,String username){
        Book book = bookRepository.findOne(bookId);
        if(book.getStatus().equals(2)){
            return "Borrowed forbidden ";
        }
        User user = userRepository.findByName(username);
        if(user==null){
            return "Student doesn't exist";
        }
        if(user.getStatus().equals(0)){
            return "Student is forbidden";
        }
        System.out.println(book.getbStatus());
        if(book.getbStatus().equals(0)){
            return "Book doesn't return";
        }
        List<BorrowBooksTable> borrowBooksList = borrowBookRepository.findAllByUserAndStatus(user.getId(),1);
        List<BorrowBooksTable> list = getNeedFineBook(user.getId());
        if (list.size()>0) {
            return "You should pay arrearage";
        }
        if(borrowBooksList.size()>=2){
            return "You have borrowed books more than 2";
        }

        BorrowBooksTable borrowBooksTable = new BorrowBooksTable();
        borrowBooksTable.setBookId(bookId);
        borrowBooksTable.setCreateDate(new Date());
        borrowBooksTable.setStatus(1);
        borrowBooksTable.setUserId(user.getId());
        book.setbStatus(0);
        bookRepository.save(book);
        bookInventoryRepository.borrowBook(book.getName());
        borrowBookRepository.save(borrowBooksTable);
        return "Borrow book successfully";
    }


    public String ReturnBook(Long bId){
        BorrowBooksTable borrowBooksTable = borrowBookRepository.findoneByBookIdAndStatus(bId,1);
        Book book = bookRepository.findOne(bId);
        User user = userRepository.getOne(borrowBooksTable.getUserId());
        Date returnTime = new Date();
        Date borrowTime = borrowBooksTable.getCreateDate();
        Long days = (returnTime.getTime()-borrowTime.getTime())/(1000*60*60*24);
        if(days>=15){
            return "You should pay arrearage";
        }else {
            book.setbStatus(1);
            bookRepository.save(book);
            borrowBooksTable.setStatus(2);
            borrowBookRepository.save(borrowBooksTable);
            BookInventory bookInventory = bookInventoryRepository.findByBookName(book.getName(),1);
            bookInventory.setBookBorrowQuantity(bookInventory.getBookBorrowQuantity()-1);
            bookInventoryRepository.save(bookInventory);
        }
        return "Returned successfully";
    }


    public List<BorrowBooksTable> getNeedFineBook(Long userID) {
        Date date = DateUtils.getBeforeDays(new Date(),15);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String d2 = format.format(date);
        Specification<BorrowBooksTable> specification = new Specification<BorrowBooksTable>() {
            @Override
            public Predicate toPredicate(Root<BorrowBooksTable> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Date> createDate = root.get("createDate");
                Path<Long> userId = root.get("userId");
                Path<Integer> status = root.get("status");
                Predicate p1 = criteriaBuilder.equal(status,"1");
                Predicate p2 = criteriaBuilder.equal(userId,userID);
                Predicate p3 = criteriaBuilder.lessThan(createDate.as(String.class),d2);
                return criteriaBuilder.and(p1,p2,p3);
            }
        };
        return borrowBookRepository.findAll(specification);
    }

    public List<User> getAdminList(){
        List<User> AdminList = new ArrayList<>();
        List<UserRole> list = userRoleRepository.findAll();
        for(UserRole u : list){
            if(u.getRoleId()==1L){
                User admin = userRepository.findOne(u.getUserId());
                    AdminList.add(admin);
            }
        }
        return AdminList;
    }

    public List<User> getUserList(){
        List<User> UserList = new ArrayList<>();
        List<UserRole> list = userRoleRepository.findAll();
        for(UserRole u : list){
            if(u.getRoleId()==2L){
                User user = userRepository.findOne(u.getUserId());
                    UserList.add(user);
            }
        }
        return UserList;
    }

    public String modify(String email,String phone,Long id){
        User user = userRepository.findOne(id);
        user.setNum(phone);
        user.setEmail(email);
        userRepository.save(user);
        return "Modify Successfully";
    }

    public BookList getSpecifyBookList(String bookname){
       List<Book> list = bookRepository.findAllByName(bookname,1);
       List<String> borrower = new ArrayList<>();
       for(Book b:list){
           BorrowBooksTable borrowBooksTable = borrowBookRepository.findoneByBookIdAndStatus(b.getId(),1);
           if(borrowBooksTable==null){
               borrower.add("");
           }else {
               User user = userRepository.getOne(borrowBooksTable.getUserId());
               borrower.add(user.getName());
           }
       }
       BookList bookList = new BookList();
        bookList.setSpecifyBookList(list);
        bookList.setBorrower(borrower);
        return bookList;
    }

    public String payMoney(Long id){
        List<BorrowBooksTable> borrowBooksTableList =borrowBookRepository.findAllByUserAndStatus(id,1);
        User user = userRepository.getOne(id);
        for(BorrowBooksTable b:borrowBooksTableList){
            Book book = bookRepository.getOne(b.getBookId());
            book.setbStatus(1);
            bookRepository.save(book);
            BookInventory bookInventory = bookInventoryRepository.findByBookName(book.getName(),1);
            bookInventory.setBookBorrowQuantity(bookInventory.getBookBorrowQuantity()-1);
            bookInventoryRepository.save(bookInventory);
            Long arrearage = (new Date().getTime()-b.getCreateDate().getTime())/86400000-15;
            b.setStatus(2);
            borrowBookRepository.save(b);
            Fine fine = new Fine();
            fine.setuId(id);
            fine.setName(user.getName());
            fine.setIsbn(book.getIsbn());
            fine.setBookId(b.getBookId());
            fine.setBookName(book.getName());
            fine.setStatus(1);
            fine.setFine(arrearage+"");
            fine.setBorrowBookDate(b.getCreateDate());
            fine.setCreateDate(new Date());
            fineRepository.save(fine);
        }
        return "Paid money successfully";
    }

    public String deleteBook(Long bookId){
       Book book = bookRepository.findOne(bookId);
       book.setStatus(0);
       BookInventory bookInventory =bookInventoryRepository.findByBookName(book.getName(),1);
       bookInventory.setBookTotalQuantity(bookInventory.getBookTotalQuantity()-1);
       if(bookInventory.getBookTotalQuantity()==0){
           bookInventory.setStatus(0);
       }
       bookInventoryRepository.save(bookInventory);
       return "Deleted successfully";
    }
}
