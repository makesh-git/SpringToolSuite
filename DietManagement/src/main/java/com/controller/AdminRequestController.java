package com.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.LogRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.model.admin.AdminInbox;
import com.model.admin.AdminOlderPost;
import com.model.admin.AdminOutbox;
import com.model.admin.AdminViewPost;
import com.model.admin.RegUsers;
import com.model.admin.Users;
import com.model.common.Batches;
import com.model.common.GeneratedCode;
import com.model.common.Groups;
import com.model.common.LoginCredentials;
import com.model.common.MotiBatch;
import com.model.common.Motivator;
import com.model.common.assignMoti;
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
import com.service.PasswordGenerator;

@RestController
@ResponseBody
@CrossOrigin("http://localhost:4200")

/*
 * 
 * It handles all the request from admin
 * 
 */
public class AdminRequestController {

/////////////////////////////////////////////  Repositories /////////////////////////////////////////////////////////////////

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

	// Admin Repositories
	@Autowired
	AdminOutboxRepo aoRepo;
	@Autowired
	AdminInboxRepo aiRepo;
	@Autowired
	AdminOlderPostRepo aopRepo;
	@Autowired
	AdminViewPostReop avpRepo;

	// Motivator Repositories
	@Autowired
	MotiInboxRepo miRepo;
	@Autowired
	MotiOutboxRepo moRepo;
	@Autowired
	MotiViewPostRepo mvpRepo;
	@Autowired
	MotiOlderPostRepo mopRepo;

	// User Repositories
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

	// To view all Motivators..
	@GetMapping("/viewMoti")
	public List<Motivator> viewMoti() {
		return (List<Motivator>) motiRepo.findAll();
	}

	// To get one motivator based on id..
	@GetMapping("/getOneMoti/{id}")
	public Optional<Motivator> getOneMoti(@PathVariable String id) {
		return motiRepo.findById(id);
	}

	// To modify Motivator details...
	@PostMapping("/modifyMoti")
	public HttpStatus modifyMoti(@RequestBody Motivator moti) {
		motiRepo.save(moti);
		return HttpStatus.CREATED;
	}

	// To delete Motivator based on id...
	@DeleteMapping("/removeMoti/{id}")
	public HttpStatus removeMoti(@PathVariable String id) {
		List<MotiBatch> mb = mbRepo.findByMotiid(id);
		mbRepo.deleteAll(mb);
		motiRepo.deleteById(id);
		logRepo.delete(logRepo.findByUserid(id));
		return HttpStatus.CREATED;
	}

	// To add new user...
	@PostMapping("/addUser")
	public HttpStatus addUser(@RequestBody Users u) {
		DateAndTimeFinder dtm = new DateAndTimeFinder();
		u.setRegDate(dtm.getCurrentDateAndTime());
		u.setUserid(u.getEmail());

		PasswordGenerator pg = new PasswordGenerator();
		u.setPass(pg.getRandomString(6));
		u.setRefId(pg.getRandomString(5));
		// Genarte PassWOrd, Refid - Save in log details..
		u.setRegDate(dtm.getCurrentDateAndTime());

		userRepo.save(u);
		LoginCredentials lg = new LoginCredentials();
		lg.setUserid(u.getUserid());
		lg.setPassword(u.getPass());
		lg.setType("User");
		logRepo.save(lg);

		String TO = u.getEmail();
		String SUB = "Online Diet System ||Motivator Registration ";
		String MSG = "Hi " + u.getName()
				+ " \n\n Congratulations, You are now a added as User of this  program by Admin"
				+ "\n\n Your Login Credentials \n User ID - " + u.getEmail() + " \n Password - " + u.getPass()
				+ " \n Referral code - " + u.getRefId() + "\n  Batch Name - " + u.getBatch().getBatchname() + " \n"
				+ "\n You can change your passowrd any time by login your account. \n\n If you have any queries means, Please reach us through onlineDietManagement@diet.com"
				+ "\n\n Thank You, \n Have a Healthy Life !";

		System.out.println();
		System.out.println("-----User Added Successfully------ ");
		System.out.println("Generated UserId : "+u.getUserid());
		System.out.println("Generated Password : "+u.getPass());
		System.out.println("-----------xxxxxxxx-----------");
		System.out.println();
		System.out.println("Sending Mail.....");
		notification.sendNotification(TO, SUB, MSG);
		return HttpStatus.CREATED;

	}

	// To add new Motivator...
	@PostMapping("/addMotivator")
	public HttpStatus addMotivator(@RequestBody Motivator m) {
		DateAndTimeFinder dtm = new DateAndTimeFinder();
		m.setRegDate(dtm.getCurrentDateAndTime());
		m.setMotiid(m.getEmail());

		PasswordGenerator pg = new PasswordGenerator();
		m.setPass(pg.getRandomString(6));
		m.setRefId(pg.getRandomString(5));

		// GEnerate Passod, Save in lo table
		motiRepo.save(m);
MotiBatch mb = new MotiBatch();
mb.setBatch(m.getBatches()); mb.setMotiid(m.getMotiid());
mbRepo.save(mb);
		
		LoginCredentials lg = new LoginCredentials();
		lg.setUserid(m.getMotiid());
		lg.setPassword(m.getPass());
		lg.setType("Motivator");
		logRepo.save(lg);
		String TO = m.getEmail();

		String SUB = "Online Diet System ||Motivator Registration ";
		String MSG = "Hi " + m.getName()
				+ " \n\n Congratulations, You are now a added as Motivator of this  program by Admin"
				+ "\n\n Your Login Credentials \n User ID - " + m.getEmail() + " \n Password - " + m.getPass()
				+ " \n Referral code - " + m.getRefId() + "\n  Motivating Batch Name - " + m.getBatches() + " \n"
				+ "\n You can change your passowrd any time by login your account. \n\n If you have any queries means, Please reach us through onlineDietManagement@diet.com"
				+ "\n\n Thank You, \n Have a Healthy Life !";

		System.out.println();
		System.out.println("-----Motivator Added Successfully------ ");
		System.out.println("Generated UserId : "+m.getMotiid());
		System.out.println("Generated Password : "+m.getPass());
		System.out.println("-----------xxxxxxxx-----------");
		System.out.println();
		System.out.println("Sending Mail.....");
		notification.sendNotification(TO, SUB, MSG);

		return HttpStatus.CREATED;

	}

	// To view all users...
	@GetMapping("/viewUsers")
	public List<Users> viewUsers() {

		return (List<Users>) userRepo.findAll();
	}

	// To get one user based on id...
	@GetMapping("/getOneUsers/{id}")
	public Optional<Users> getOneUsers(@PathVariable String id) {
		return userRepo.findById(id);
	}

	// To modify user details...
	@PostMapping("/modifyUser")
	public HttpStatus modifyUser(@RequestBody Users user) {
		userRepo.save(user);
		return HttpStatus.CREATED;
	}

	// To delete User details...
	@DeleteMapping("/removeUser/{id}")
	public HttpStatus removeUser(@PathVariable String id) {
		logRepo.delete(logRepo.findByUserid(id));
		userRepo.deleteById(id);
		return HttpStatus.CREATED;
	}

	// To get registered users...
	@GetMapping("/getRegUsers")
	public List<RegUsers> regUsers() {
		return (List<RegUsers>) regRepo.findAll();
	}

	// To get reg registered user...
	@GetMapping("/getOneRegUsers/{id}")
	public Optional<RegUsers> getOneRegUsers(@PathVariable String id) {
		return regRepo.findById(id);
	}

	// To save the registered users details...
	@PostMapping("/postSaveRegUsers")
	public HttpStatus postSaveRegUsers(@RequestBody RegUsers reg) {


		if (reg.getGender().equals("Male")) {
			reg.setPreg("Not Applicable");
		}
		if (reg.getMedRes() == null)
			reg.setMedRes("Not Mentioned");
		if (reg.getDietRes() == null)
			reg.setDietRes("Not Mentioned");
	
		if (reg.getRef() == null) {
			reg.setRef("Not Applicable");
		} else {
			List<Users> u = userRepo.findByrefId(reg.getRef());
			List<Motivator> m = motiRepo.findByRefId(reg.getRef());
			if (!u.isEmpty()) {
				reg.setRef(reg.getRef() + "-" + u.get(0).getUserid());
			} else if (!m.isEmpty()) {
				reg.setRef(reg.getRef() + "-" + m.get(0).getMotiid());
			} else {
				reg.setRef(reg.getRef() + "Not Found");
			}

		}
		DateAndTimeFinder dtm = new DateAndTimeFinder();
		reg.setRegDate(dtm.getCurrentDateAndTime());
		regRepo.save(reg);
		return HttpStatus.CREATED;
	}

	// To get all batches...
	@GetMapping("getAllBatches")
	public List<Batches> getAllBatches() {
		return (List<Batches>) batchRepo.findAll();
	}

	// To get all groups...
	@GetMapping("getAllGroups")
	public List<Groups> getAllGroups() {
		return (List<Groups>) groupRepo.findAll();
	}

	// To approve the regstered users...
	@PostMapping("setApproveUsers/{id}/{batch}/{group}")
	public void setApproveUsers(@PathVariable("id") String id, @PathVariable("batch") String batch,
			@PathVariable("group") String group) {

		DateAndTimeFinder dtm = new DateAndTimeFinder();
		Batches b = batchRepo.findByBatchname(batch).get(0);
		Groups g = groupRepo.findByGroupname(group).get(0);
		// Generate PAssowrd, Refid, find Ref by, Send mail, Save in Log details

		PasswordGenerator pg = new PasswordGenerator();

		Optional<RegUsers> rg = regRepo.findById(id);
		Users u = new Users(rg.get().getEmail(), rg.get().getName(), rg.get().getAge(), rg.get().getEmail(), b, g,
				rg.get().getGender(), rg.get().getMobile(), rg.get().getAddress(), rg.get().getCountry(),
				rg.get().getState(), rg.get().getCity(), rg.get().getPincode(), rg.get().getHeight(),
				rg.get().getWeight(), rg.get().getBmi(), rg.get().getReason(), rg.get().getMedRes(),
				rg.get().getDietRes(), rg.get().getFood(), rg.get().getPreg(), "REF", dtm.getCurrentDateAndTime(),
				pg.getRandomString(5), pg.getRandomString(6));

		userRepo.save(u);

		LoginCredentials lg = new LoginCredentials();
		lg.setUserid(u.getUserid());
		lg.setPassword(u.getPass());
		lg.setType("User");
		logRepo.save(lg);

		String TO = u.getEmail();

		
		String SUB = "Online Diet System || Registration ";
		String MSG = "Hi " + u.getName()
				+ " \n\n Congratulations, You request for joining for Diet Management Program is approved"
				+ "\n\n Your Login Credentials \n User ID - " + u.getEmail() + " \n Password - " + u.getPass()
				+ " \n Referral code - " + u.getRefId() + "\n Batch Name - " + u.getBatch().getBatchname()
				+ " \n Group Name - " + group + "\n"
				+ "\n You can change your passowrd any time by login your account. \n\n If you have any queries means, Please reach us through onlineDietManagement@diet.com"
				+ "\n\n Thank You, \n Have a Healthy Life !";
		System.out.println();
		System.out.println("-----Request Approved------ ");
		System.out.println("Generated UserId : "+u.getUserid());
		System.out.println("Generated Password : "+u.getPass());
		System.out.println("-----------xxxxxxxx-----------");
		System.out.println();
		System.out.println("Sending Mail.....");
		notification.sendNotification(TO, SUB, MSG);

		regRepo.deleteById(id);
//		return HttpStatus.CREATED;
	}

	// To reject the registered users...
	@PostMapping("/setRejectUsers/{id}/{reason}")
	public HttpStatus setRejectUsers(@PathVariable("id") String id, @PathVariable("reason") String reason) {
		// Delete from regUsers repo,
		regRepo.deleteById(regRepo.findById(id).get().getEmail());

		// Send Rejection mail
		String TO = id;

		String SUB = "Online Diet System || Registration ";
		String MSG = "Hi User \n Thankyou for your Registration, Our team reviewed your details and they found that your are not eligible"
				+ "for this program. \n Reason - " + reason + " \n Thank you";

		
		System.out.println("--------Request Rejected------------");
		System.out.println("Sending Mail.....");
		notification.sendNotification(TO, SUB, MSG);

		PasswordGenerator pg = new PasswordGenerator();
		return HttpStatus.CREATED;

	}

	// To create a new batch...
	@PostMapping("/postCreateBatch")
	public HttpStatus postCreateBatch(@RequestBody Batches b) {
		DateAndTimeFinder dtm = new DateAndTimeFinder();
		b.setStrtdate(dtm.getCurrentDateAndTime());
		batchRepo.save(b);
		return HttpStatus.CREATED;
	}

	// To create a new group ...
	@PostMapping("/postCreateGroup")
	public HttpStatus postCreateGroup(@RequestBody Groups g) {
		DateAndTimeFinder dtm = new DateAndTimeFinder();
		g.setStrtdate(dtm.getCurrentDateAndTime());
		groupRepo.save(g);
		return HttpStatus.CREATED;
	}

	@PostMapping("/postAddUser")
	public HttpStatus postAddUser(@RequestBody Motivator moti) {
		motiRepo.save(moti);
		return HttpStatus.CREATED;
	}

	@PostMapping("/postAddMoti")
	public HttpStatus postAddMoti(@RequestBody Users user) {
		userRepo.save(user);
		return HttpStatus.CREATED;
	}

	/*
	 * 
	 * Admin Message & Post Requests.....
	 * 
	 */

	// To save in admin outbox...
	@PostMapping("/postAdminOutbox")
	public HttpStatus adminOutbox(@RequestBody AdminOutbox ao) {

		DateAndTimeFinder dtm = new DateAndTimeFinder();
		ao.setDtm(dtm.getCurrentDateAndTime());
		aoRepo.save(ao);
		return HttpStatus.CREATED;
	}

	// To save in inbox...
	@PostMapping("postAdminInbox")
	public HttpStatus postAdminInbox(@RequestBody AdminInbox inb) {
		DateAndTimeFinder dtm = new DateAndTimeFinder();
		inb.setDtm(dtm.getCurrentDateAndTime());
		aiRepo.save(inb);
		return HttpStatus.CREATED;
	}

	// To get admin out box...
	@GetMapping("getAdminOutbox")
	public List<AdminOutbox> getAdminOutbox() {
		return (List<AdminOutbox>) aoRepo.findAll();
	}

	// To get admin msg from motiavtors...
	@GetMapping("getAdminInboxFrmMoti")
	public List<AdminInbox> getAdminInboxFrmMoti() {
		return aiRepo.findByType("Motivator");
	}

	// To get admin msg from users...
	@GetMapping("getAdminInboxFrmUser")
	public List<AdminInbox> getAdminInboxFrmUser() {
		return aiRepo.findByType("User");
	}

	// To get admin inbox
	@GetMapping("getAdminInbox")
	public List<AdminInbox> getAdminInbox() {
		return (List<AdminInbox>) aiRepo.findAll();
	}

	// To save in admin older post..
	@PostMapping("postAdminOlderPost")
	public HttpStatus postAdminOlderPost(@RequestBody AdminOlderPost aop) {
		DateAndTimeFinder dtm = new DateAndTimeFinder();
		aop.setDtm(dtm.getCurrentDateAndTime());
		aopRepo.save(aop);
		return HttpStatus.CREATED;
	}

	// To get Admin older posts
	@GetMapping("getAdminOlderPost")
	public List<AdminOlderPost> getAdminOlderPost() {
		return (List<AdminOlderPost>) aopRepo.findAll();

	}

	// To get admins post
	@GetMapping("getAdminViewPost")
	public List<AdminViewPost> getAdminViewPost() {
		return (List<AdminViewPost>) avpRepo.findAll();
	}

	// To save in admin post
	@PostMapping("/postAdminViewPost")
	public HttpStatus postAdminViewPost(@RequestBody AdminViewPost post) {
		DateAndTimeFinder dtm = new DateAndTimeFinder();
		post.setDtm(dtm.getCurrentDateAndTime());
		avpRepo.save(post);
		return HttpStatus.CREATED;
	}

	// To check ,is motivator is present in batch
	@GetMapping("isMotiAvail/{moti}/{batch}")
	public List<MotiBatch> isMotiAvail(@PathVariable String moti, @PathVariable String batch) {
		List<MotiBatch> nn = mbRepo.findByMotiidAndBatch(moti, batch);

		return nn;
	}

	// To assign motivator...
	@PostMapping("postAssignMotivator")
	public HttpStatus postAssignMotivator(@RequestBody MotiBatch mb) {

		mbRepo.save(mb);
		Optional<Motivator> m = motiRepo.findById(mb.getMotiid());
		String batch = m.get().getBatches();
		if (batch != "")
			batch = batch + "," + mb.getBatch();
		else
			batch = batch + "" + mb.getBatch();
		m.get().setBatches(batch);
		motiRepo.save(m.get());
		return HttpStatus.CREATED;

	}

	// To get motivators of all available batches
	@GetMapping("/getAssignMotiDetails")
	public List<assignMoti> kk() {
		List<assignMoti> ret = new ArrayList<assignMoti>();

		List<Batches> b = (List<Batches>) batchRepo.findAll();
		for (Batches l : b) {
			List<Motivator> list = new ArrayList<Motivator>();

			List<MotiBatch> mb = mbRepo.findByBatch(l.getBatchname());
			for (MotiBatch m : mb) {

				list.add(motiRepo.findById(m.getMotiid()).get());
			}
			ret.add(new assignMoti(l.getBatchname(), list));
		}

		return ret;
	}

	// To de assign the motivator from particular batch...
	@DeleteMapping("deAssignMoti/{batch}/{id}")
	public HttpStatus deAssign(@PathVariable String batch, @PathVariable String id) {

		List<MotiBatch> mb = mbRepo.findByMotiidAndBatch(id, batch);
		if (!mb.isEmpty())
			mbRepo.delete(mb.get(0));

		Optional<Motivator> m = motiRepo.findById(id);
		String curBatch = m.get().getBatches();

		String newbatch = "";
		String arr[] = curBatch.split(",");
		for (int i = 0; i < arr.length; i++) {

			if (arr[i].equals(batch)) {
				continue;
			} else {
				if (newbatch != "")
					newbatch = newbatch + "," + arr[i];
				else
					newbatch = newbatch + "" + arr[i];
			}
		}
		m.get().setBatches(newbatch);
		motiRepo.save(m.get());
		return HttpStatus.CREATED;

	}

	// To delete admin post
	@PostMapping("adminDeletePost")
	public HttpStatus adminDeletepost(@RequestBody AdminOlderPost post) {


		String frm = post.getFrm();
		String dtm = post.getDtm();
		String to = post.getTo();
		if (post.getTo().equals("Everyone")) {
			uvpRepo.delete(uvpRepo.findByFrmAndDtm(frm, dtm));
			mvpRepo.delete(mvpRepo.findByFrmAndDtm(frm, dtm));
		} else if (post.getTo().equals("Motivators")) {
			mvpRepo.delete(mvpRepo.findByFrmAndDtm(frm, dtm));
		} else {
			uvpRepo.delete(uvpRepo.findByFrmAndDtm(frm, dtm));
		}

		aopRepo.delete(post);
		return HttpStatus.CREATED;
	}

	// To delete admin message
	@PostMapping("adminDeleteMessage")
	public HttpStatus adminDeleteMapping(@RequestBody AdminOutbox out) {
		String frm = out.getFrm();
		String to = out.getTo();
		String dtm = out.getDtm();
		String type = out.getType();

		if (type.equals("User")) {

			uiRepo.delete(uiRepo.findByFrmAndDtm(frm, dtm));
		}

		else if (type.equals("Motivator")) {

			miRepo.delete(miRepo.findByFrmAndDtm(frm, dtm));

		}

		aoRepo.delete(out);
		return HttpStatus.CREATED;

	}

	// To remove admin inbox message..
	@PostMapping("adminDeleteInbox")
	public HttpStatus adminDeleteInbox(@RequestBody AdminInbox in) {
		aiRepo.delete(in);
		return HttpStatus.CREATED;
	}

	// To remove admin view post
	@PostMapping("adminDeleteViewPost")
	public HttpStatus adminDeleteViewPost(@RequestBody AdminViewPost post) {
		avpRepo.delete(post);
		return HttpStatus.CREATED;
	}
	
	
	//To convert existing user to Motivator...
	@PostMapping("/convertUserToMoti")
	public HttpStatus convertUserToMoti(@RequestBody Users u) {
	
		Motivator m = new Motivator(u.getUserid(), u.getName(), u.getEmail(), u.getAge(), 
				u.getBatch().getBatchname(), u.getPass(), u.getGender(), u.getMobile(),
				u.getCity(), u.getAddress(), u.getCountry(), u.getState(), u.getCity(),
				u.getPincode(), u.getHeight(), u.getWeight(), u.getRegDate(), u.getRefId());
		
	LoginCredentials l = logRepo.findByUserid(u.getUserid());
	l.setType("Motivator");
	logRepo.save(l);
	
	userRepo.delete(userRepo.findById(u.getUserid()).get());
	motiRepo.save(m);
	MotiBatch mb = new MotiBatch(m.getMotiid(), m.getBatches());
	mbRepo.save(mb);
		
		return HttpStatus.OK;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
