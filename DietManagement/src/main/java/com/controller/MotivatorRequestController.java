package com.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.model.common.Batches;
import com.model.common.Groups;
import com.model.common.MotiBatch;
import com.model.motivator.MotiInbox;
import com.model.motivator.MotiOlderPost;
import com.model.motivator.MotiOutbox;
import com.model.motivator.MotiViewPost;
import com.model.motivator.MotivatingBatches;
import com.model.user.UserDailyLog;
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
 * It is reponsible for handling requests from motivators..
 * 
 * 
 */
public class MotivatorRequestController {

//////////////////////////////////////////////////////// Repositories ////////////////////////////////////////////////////////

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

	// Admin
	@Autowired
	AdminOutboxRepo aoRepo;
	@Autowired
	AdminInboxRepo aiRepo;
	@Autowired
	AdminOlderPostRepo aopRepo;
	@Autowired
	AdminViewPostReop avpRepo;

	// Motivator
	@Autowired
	MotiInboxRepo miRepo;
	@Autowired
	MotiOutboxRepo moRepo;
	@Autowired
	MotiViewPostRepo mvpRepo;
	@Autowired
	MotiOlderPostRepo mopRepo;

	// User
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

	/*
	 * 
	 * To handle motivator message and post requests...
	 * 
	 */

	// To save in motivator inbox..
	@PostMapping("postMotiInbox")
	public HttpStatus postMotiInbox(@RequestBody MotiInbox inb) {
		DateAndTimeFinder dtm = new DateAndTimeFinder();
		inb.setDtm(dtm.getCurrentDateAndTime());
		miRepo.save(inb);
		return HttpStatus.CREATED;
	}

	// To save in motivator outbox..
	@PostMapping("postMotiOutbox")
	public HttpStatus postMotiOutbox(@RequestBody MotiOutbox out) {

		DateAndTimeFinder dtm = new DateAndTimeFinder();
		out.setDtm(dtm.getCurrentDateAndTime());
		moRepo.save(out);
		return HttpStatus.CREATED;
	}

	// To get motivator outbox based on id..
	@GetMapping("getMotiOutbox/{id}")
	public List<MotiOutbox> getMotiOutbox(@PathVariable String id) {

		return (List<MotiOutbox>) moRepo.findByFrm(id);
	}

	@GetMapping("getMotiInboxFrmAdmin")
	public List<MotiInbox> getMotiInboxFrmAdmin() {
		return miRepo.findByType("Motivator");
	}

	@GetMapping("getMotiInboxFrmUser")
	public List<MotiInbox> getMotiInboxFrmUser() {
		return miRepo.findByType("Admin");
	}

	// To get Motivator inbox based on id..
	@GetMapping("getMotiInbox/{id}")
	public List<MotiInbox> getMotiInbox(@PathVariable String id) {

		return (List<MotiInbox>) miRepo.findByTo(id);
	}

	// To save in motivator view post
	@PostMapping("/postMotiViewPost")
	public HttpStatus postMotiViewPost(@RequestBody MotiViewPost post) {
		DateAndTimeFinder dtm = new DateAndTimeFinder();
		post.setDtm(dtm.getCurrentDateAndTime());
		mvpRepo.save(post);
		return HttpStatus.CREATED;
	}

	// To save in motivator older post
	@PostMapping("/postMotiOlderPost")
	public HttpStatus postMotiOlderPost(@RequestBody MotiOlderPost post) {
		DateAndTimeFinder dtm = new DateAndTimeFinder();
		post.setDtm(dtm.getCurrentDateAndTime());
		mopRepo.save(post);
		return HttpStatus.CREATED;
	}

	// To get motivator older post based on id...
	@GetMapping("/getMotiOlderPost/{id}")
	public List<MotiOlderPost> getMotiOlderPost(@PathVariable String id) {
		return (List<MotiOlderPost>) mopRepo.findByFrm(id);
	}

	// To get motivator view post...
	@GetMapping("/getMotiViewPost")
	public List<MotiViewPost> getMotiViewPost() {
		return (List<MotiViewPost>) mvpRepo.findAll();
	}

	// To get the batch and grp
	@GetMapping("/getBatchAndGroup")
	public List<String> CMB() {

		List<Batches> liB = (List<Batches>) batchRepo.findAll();
		List<Groups> liG = (List<Groups>) groupRepo.findAll();
		List<String> com = new ArrayList<String>();
		for (int i = 0; i < liG.size(); i++) {
			for (int j = 0; j < liB.size(); j++) {
				com.add(liB.get(j).getBatchname() + "-" + liG.get(i).getGroupname());
			}
		}
		return com;

	}

	// To get all mootivating batches..
	@GetMapping("getMotivatingBatches/{id}")
	public List<Batches> getMotivatingBatches(@PathVariable String id) {

	
		List<MotiBatch> mb = mbRepo.findByMotiid(id);
	
		List<Batches> list = new ArrayList<Batches>();
		for (MotiBatch i : mb) {

			List<Batches> b = batchRepo.findByBatchname(i.getBatch());

			list.addAll(b);
		}

		return list;
	}

	// To get all motivating users..
	@GetMapping("getMotivatingUsers/{id}")
	public List<Users> getMotivatingUsers(@PathVariable String id) {
		List<MotiBatch> mb = mbRepo.findByMotiid(id);
		List<Users> list = new ArrayList<Users>();
		for (MotiBatch i : mb) {
			List<Batches> b = batchRepo.findByBatchname(i.getBatch());
			List<Users> u = userRepo.findByBatch(b.get(0));
			list.addAll(u);
		}
		return list;
	}

	public List<MotivatingBatches> ll(@PathVariable String id) {

		List<MotiBatch> mb = mbRepo.findByMotiid(id);
		List<MotivatingBatches> list = new ArrayList<MotivatingBatches>();
		for (MotiBatch i : mb) {
			List<Batches> b = batchRepo.findByBatchname(i.getBatch());
			List<Users> u = userRepo.findByBatch(b.get(0));
		}

		return list;
	}

	// To get Dailylogs...
	@GetMapping("getDailyLog")
	public List<UserDailyLog> getMotiDailyLog() {

		return (List<UserDailyLog>) udlRepo.findAll();
	}

	// To find Dailylogs based on date..
	@GetMapping("getDailyLogByDate/{date}")
	public List<UserDailyLog> getDailyLog(@PathVariable String date) {
		return udlRepo.findByDatee(date);
	}

	// Too get daily log based on date and id..
	@GetMapping("getDailyLogByDate/{id}/{date}")
	public List<UserDailyLog> getDailyLogByDate(@PathVariable String id, @PathVariable String date) {

		List<MotiBatch> mb = mbRepo.findByMotiid(id);
		System.out.println("MOTIBATCH");
		System.out.println(mb);
		List<UserDailyLog> list = new ArrayList<UserDailyLog>();
		for (MotiBatch i : mb) {
			System.out.println("Itera ");
			System.out.println(i);
			list.addAll(udlRepo.findByBatchAndDatee(i.getBatch(), date));
//			List<Batches> b = batchRepo.findByBatchname(i.getBatch());
//			System.out.println(b);
//			List<Users> u = userRepo.findByBatch(b.get(0));
//			System.out.println(u);
//			for (Users ul : u) {
////				
//				System.out.println("IN SECON ITER");
//				System.out.println(ul);
//				list.addAll(udlRepo.findByBatchAndDatee(ul.getBatch().getBatchname(), date));
//			}

		}
		System.out.println(list);
		return list;
	}

	// To find the dailylogs based on batch..
	@GetMapping("getDailyLogByBatch/{batch}")
	public List<UserDailyLog> getDailyLogByBatch(@PathVariable String batch) {

		return udlRepo.findByBatch(batch);
	}

	// To get dailogs of the users (based on motivator id)
	@GetMapping("getDailyLogByMotivator/{id}")
	public List<UserDailyLog> getDailyLogByMotivator(@PathVariable String id) {

		
		List<MotiBatch> mb = mbRepo.findByMotiid(id);

		List<UserDailyLog> list = new ArrayList<UserDailyLog>();

		for (MotiBatch i : mb) {
			
			List<Batches> b = batchRepo.findByBatchname(i.getBatch());
			List<Users> u = userRepo.findByBatch(b.get(0));
		
			for (Users ul : u) {
				
				list.addAll(udlRepo.findByFrm(ul.getUserid()));
		
			}

		}
;
		return list;
	}

	// To remove motivatot post..
	@PostMapping("motivatorDeletePost")
	public HttpStatus motivatorDeletePost(@RequestBody MotiOlderPost post) {

		String frm = post.getFrm();
		String dtm = post.getDtm();
		String to = post.getTo();
		if (post.getTo().equals("Everyone")) {
			uvpRepo.delete(uvpRepo.findByFrmAndDtm(frm, dtm));
			avpRepo.delete(avpRepo.findByFrmAndDtm(frm, dtm));
		} else if (post.getTo().equals("Admin")) {
			avpRepo.delete(avpRepo.findByFrmAndDtm(frm, dtm));
		} else {
			uvpRepo.delete(uvpRepo.findByFrmAndDtm(frm, dtm));
		}

		mopRepo.delete(post);
		return HttpStatus.CREATED;
	}

	// To remove motivator message.
	@PostMapping("motivatorDeleteMessage")
	public HttpStatus motivatorDeleteMessage(@RequestBody MotiOutbox out) {
		String frm = out.getFrm();
		String to = out.getTo();
		String dtm = out.getDtm();
		String type = out.getType();

		if (type.equals("User")) {

			uiRepo.delete(uiRepo.findByFrmAndDtm(frm, dtm));
		}

		else if (type.equals("Admin")) {

			aiRepo.delete(aiRepo.findByFrmAndDtm(frm, dtm));

		}

		moRepo.delete(out);
		return HttpStatus.CREATED;

	}

	// To delete motivator inbox...
	@PostMapping("motivatorDeleteInbox")
	public HttpStatus motivatorDeleteInbox(@RequestBody MotiInbox in) {
		miRepo.delete(in);
		return HttpStatus.CREATED;
	}

	// To delete Motivator view post..
	@PostMapping("motivatorDeleteViewPost")
	public HttpStatus motivatorDeleteViewPost(@RequestBody MotiViewPost post) {
		mvpRepo.delete(post);
		return HttpStatus.CREATED;

	}

}
