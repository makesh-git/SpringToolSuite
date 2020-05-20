package com.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.model.admin.Users;
import com.model.common.WeeklyDiet;
import com.model.user.UserDailyLog;
import com.model.user.UserInbox;
import com.model.user.UserMonthlyMeas;
import com.model.user.UserOlderPost;
import com.model.user.UserOutbox;
import com.model.user.UserViewPost;
import com.repository.admin.AdminInboxRepo;
import com.repository.admin.AdminOlderPostRepo;
import com.repository.admin.AdminOutboxRepo;
import com.repository.admin.AdminViewPostReop;
import com.repository.admin.RegUsersRepo;
import com.repository.admin.UsersRepo;
import com.repository.common.BatchRepo;
import com.repository.common.FileUploadRepo;
import com.repository.common.GroupRepo;
import com.repository.common.LoginCredentialsRepo;
import com.repository.common.MotiBatchRepo;
import com.repository.common.MotivatorRepo;
import com.repository.common.WeeklyDietRepo;
import com.repository.motivator.MotiInboxRepo;
import com.repository.motivator.MotiOlderPostRepo;
import com.repository.motivator.MotiOutboxRepo;
import com.repository.motivator.MotiViewPostRepo;
import com.repository.user.UserDailyLogRepo;
import com.repository.user.UserInboxRepo;
import com.repository.user.UserMonthlyMeasRepo;
import com.repository.user.UserOlderPostRepo;
import com.repository.user.UserOutboxRepo;
import com.repository.user.UserViewPostRepo;
import com.service.DateAndTimeFinder;
import com.service.NotificatioService;

@RestController
@ResponseBody
@CrossOrigin("http://localhost:4200")

/*
 * 
 * To handle the requests from users..
 * 
 * 
 */
public class UserRequestController {

////////////////////////////////////////////////////////Repositories ////////////////////////////////////////////////////////

	@Autowired
	private NotificatioService notification;
	@Autowired
	LoginCredentialsRepo logRepo;

	@Autowired
	BatchRepo batchRepo;
	@Autowired
	GroupRepo groupRepo;
	@Autowired
	UsersRepo userRepo;
	@Autowired
	RegUsersRepo regRepo;
	@Autowired
	MotivatorRepo motiRepo;
	@Autowired
	MotiBatchRepo mbRepo;

//Admin
	@Autowired
	AdminOutboxRepo aoRepo;
	@Autowired
	AdminInboxRepo aiRepo;
	@Autowired
	AdminOlderPostRepo aopRepo;
	@Autowired
	AdminViewPostReop avpRepo;

//Motivator
	@Autowired
	MotiInboxRepo miRepo;
	@Autowired
	MotiOutboxRepo moRepo;
	@Autowired
	MotiViewPostRepo mvpRepo;
	@Autowired
	MotiOlderPostRepo mopRepo;

//User
	@Autowired
	UserInboxRepo uiRepo;
	@Autowired
	UserOutboxRepo uoRepo;
	@Autowired
	UserViewPostRepo uvpRepo;
	@Autowired
	UserDailyLogRepo udlRepo;
	@Autowired
	UserMonthlyMeasRepo ummRepo;
	@Autowired
	UserOlderPostRepo uopRepo;
	@Autowired
	FileUploadRepo fupRepo;
	@Autowired
	WeeklyDietRepo wdRepo;

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	// To save in user inbox..
	@PostMapping("postUserInbox")
	public HttpStatus postUserInbox(@RequestBody UserInbox inb) {
		DateAndTimeFinder dtm = new DateAndTimeFinder();
		inb.setDtm(dtm.getCurrentDateAndTime());
		uiRepo.save(inb);return HttpStatus.CREATED;
	}

	// To save in user outbox..
	@PostMapping("postUserOutbox")
	public HttpStatus userPostOutbox(@RequestBody UserOutbox out) {
		DateAndTimeFinder dtm = new DateAndTimeFinder();
		out.setDtm(dtm.getCurrentDateAndTime());
		uoRepo.save(out);return HttpStatus.CREATED;
	}
	
	//To get user outbox based on id..
	@GetMapping("getUserOutbox/{id}")
	public List<UserOutbox> getUserOutbox(@PathVariable String id) {

		return (List<UserOutbox>) uoRepo.findByFrm(id);
	}

	@GetMapping("getUserInboxFrmMoti")
	public List<UserInbox> getUserInboxFrmMoti() {
		return uiRepo.findByType("Motivator");
	}

	
	@GetMapping("getUserInboxFrmAdmin")
	public List<UserInbox> getUserInboxFrmAdmin() {
		return uiRepo.findByType("Admin");
	}

	//To get user inbox based on id..
	@GetMapping("getUserInbox/{id}")
	public List<UserInbox> getUserInbox(@PathVariable String id) {
		return uiRepo.findByTo(id);
	}

	// To save in user view post..
	@PostMapping("/postUserViewPost")
	public HttpStatus postUserViewPost(@RequestBody UserViewPost post) {
		DateAndTimeFinder dtm = new DateAndTimeFinder();
		post.setDtm(dtm.getCurrentDateAndTime());
		uvpRepo.save(post);return HttpStatus.CREATED;
	}

	
	// To get post based on user id ...
	@GetMapping("getUserViewPost/{id}")
	public List<UserViewPost> getUserViewPost(@PathVariable String id) {
		List<UserViewPost> list = new ArrayList<UserViewPost>();
		list.addAll(uvpRepo.findByAud("Users"));
		list.addAll(uvpRepo.findByAud("Everyone"));
		String bg = "";
		if (userRepo.existsById(id)) {
			Optional<Users> u = userRepo.findById(id);
			list.addAll(uvpRepo.findByAud(u.get().getBatch().getBatchname()));
			list.addAll(uvpRepo.findByAud(u.get().getGroup().getGroupname()));
			bg = u.get().getBatch().getBatchname() + "-" + u.get().getGroup().getGroupname();
			list.addAll(uvpRepo.findByAud(bg));
		}

		return list;
	}

	// To get olderpost based on id..
	@GetMapping("getUserOlderPost/{id}")
	public List<UserOlderPost> getUserOlderPost(@PathVariable String id) {

		return uopRepo.findByFrm(id);
	}

	// To save the dailylog..
	@PostMapping("postUserDailylog")
	public HttpStatus postUserDailylog(@RequestBody UserDailyLog log) {
		DateAndTimeFinder dtm = new DateAndTimeFinder();
		log.setDtm(dtm.getCurrentDateAndTime());
		udlRepo.save(log);return HttpStatus.CREATED;
	}

	// To save the monthly measurements..
	@PostMapping("postUserMonthlyMeas")
	public HttpStatus postUserMonthlyMeas(@RequestBody UserMonthlyMeas mes) {
		ummRepo.save(mes);return HttpStatus.CREATED;
	
	}

	// To get the monythly report based on id..
	@GetMapping("getMonthlyReport/{userid}")
	public List<UserMonthlyMeas> getMonthlyReport(@PathVariable String userid) {

	
		return ummRepo.findByUserid(userid);
	}

	// To save in user older post
	@PostMapping("postUserOlderPost")
	public HttpStatus postUserOlderPost(@RequestBody UserOlderPost post) {
		DateAndTimeFinder dtm = new DateAndTimeFinder();
		post.setDtm(dtm.getCurrentDateAndTime());
		uopRepo.save(post);return HttpStatus.CREATED;
	}

	// to get user older post..
	@GetMapping("getUserOlderPost")
	public List<UserOlderPost> getUserOlderPost() {
		return (List<UserOlderPost>) uopRepo.findAll();
	}

	
	// To get user weeekly diet..
	@GetMapping("getUserWeeklyDiet/{id}")
	public List<WeeklyDiet> getUserWeeklyDiet(@PathVariable String id) {

		List<WeeklyDiet> list = new ArrayList<WeeklyDiet>();
		list.addAll(wdRepo.findByBatch("All batches"));
		if (userRepo.existsById(id)) {
			Optional<Users> u = userRepo.findById(id);
			String b = u.get().getBatch().getBatchname();

			list.addAll(wdRepo.findByBatch(b));
		}

		return list;

	}


	// To remove user created post
	@PostMapping("userDeletePost")
	public HttpStatus userDeletePost(@RequestBody UserOlderPost post) {

		String frm = post.getFrm();
		String dtm = post.getDtm();
		String to = post.getTo();


		if (post.getTo().equals("Everyone")) {
			mvpRepo.delete(mvpRepo.findByFrmAndDtm(frm, dtm));
			avpRepo.delete(avpRepo.findByFrmAndDtm(frm, dtm));
		} else if (post.getTo().equals("Admin")) {
			avpRepo.delete(avpRepo.findByFrmAndDtm(frm, dtm));
		} else {
			mvpRepo.delete(mvpRepo.findByFrmAndDtm(frm, dtm));
		}

	
		uopRepo.delete(post);return HttpStatus.CREATED;
	}

	// To remove user sent message
	@PostMapping("userDeleteMessage")
	public HttpStatus userDeleteMessage(@RequestBody UserOutbox out) {
		String frm = out.getFrm();
		String to = out.getTo();
		String dtm = out.getDtm();
		String type = out.getType();

		if (type.equals("Motivator")) {
		
			
			miRepo.delete(miRepo.findByFrmAndDtm(frm, dtm));
		}

		else if (type.equals("Admin")) {
		
			aiRepo.delete(aiRepo.findByFrmAndDtm(frm, dtm));

		}

		uoRepo.delete(out);return HttpStatus.CREATED;

	}
	// To remove user inbox msg
	@PostMapping("userDeleteInbox")
	public HttpStatus userDeleteInbox(@RequestBody UserInbox in) {
		uiRepo.delete(in);return HttpStatus.CREATED;
	}
	// To remove user view post
	@PostMapping("userDeleteViewPost")
	public HttpStatus userDeleteViewPost(@RequestBody UserViewPost post) {
		uvpRepo.delete(post);return HttpStatus.CREATED;
	}

}
