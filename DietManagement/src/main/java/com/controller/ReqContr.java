package com.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.batch.spi.Batch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.ls.LSInput;

import com.model.admin.AdminInbox;
import com.model.admin.AdminOlderPost;
import com.model.admin.AdminOutbox;
import com.model.admin.AdminViewPost;
import com.model.admin.RegUsers;
import com.model.admin.Users;
import com.model.common.Batches;
import com.model.common.FileUpload;
import com.model.common.GeneratedCode;
import com.model.common.Groups;
import com.model.common.LoginCredentials;
import com.model.common.MotiBatch;
import com.model.common.Motivator;
import com.model.common.WeeklyDiet;
import com.model.common.assignMoti;
import com.model.motivator.MotiInbox;
import com.model.motivator.MotiOlderPost;
import com.model.motivator.MotiOutbox;
import com.model.motivator.MotiViewPost;
import com.model.motivator.MotivatingBatches;
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
import com.securityWeb.CurrentLoggedUser;
import com.service.DateAndTimeFinder;
import com.service.NotificatioService;
import com.service.PasswordGenerator;

@RestController
@ResponseBody
@CrossOrigin("http://localhost:4200")
public class ReqContr {

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

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	

	// To handle successful login request..
	@GetMapping("/login")
	public LoginCredentials dd() {

		System.out.println("Login Enabled... !");
		CurrentLoggedUser c = new CurrentLoggedUser();

		return logRepo.findByUserid(c.getCurrentUser().getUserid());
	}

	/*
	 * 
	 * Search result requests
	 * 
	 */

	// To search by user type
	@GetMapping("searchByUser/{value}")
	public List<Users> searchByUser(@PathVariable String value) {

		List<Users> list = new ArrayList<Users>();
		list = userRepo.findByUseridIgnoreCase(value);
		if (list.isEmpty()) {
			list = userRepo.findByNameIgnoreCase(value);
		}
		return list;
	}

	// To search by motivator type
	@GetMapping("searchByMoti/{value}")
	public List<Motivator> searchByMoti(@PathVariable String value) {

		List<Motivator> list = new ArrayList<Motivator>();
		list = motiRepo.findByMotiidIgnoreCase(value);
		if (list.isEmpty()) {
			list = motiRepo.findByNameIgnoreCase(value);
		}
		return list;
	}

	// To search by batch type
	@GetMapping("searchByBatch/{value}")
	public List<Batches> searchByBatch(@PathVariable String value) {
		List<Batches> list = new ArrayList<Batches>();
		list = batchRepo.findByBatchnameIgnoreCase(value);

		return list;
	}

	// To get motivator details of given batch..
	@GetMapping("batchMotivatorDetails/{batch}")
	public List<Motivator> batchMotivatorDetails(@PathVariable String batch) {
		List<MotiBatch> list = mbRepo.findByBatchIgnoreCase(batch);
		List<Motivator> moti = new ArrayList<Motivator>();

		for (MotiBatch m : list) {
			String s = m.getMotiid();
			Optional<Motivator> mm = motiRepo.findById(s);
			moti.add(mm.get());
		}
		return moti;
	}

	// To get users based on given batch..
	@GetMapping("userFindByBatch/{batch}")
	public List<Users> userFindByBatch(@PathVariable String batch) {
		List<Batches> b = batchRepo.findByBatchnameIgnoreCase(batch);
		return userRepo.findByBatch(b.get(0));

	}

	// To search by group type
	@GetMapping("searchByGroup/{group}")
	public List<Groups> searchByGroup(@PathVariable String group) {
		List<Groups> list = groupRepo.findByGroupnameIgnoreCase(group);
		return list;
	}

	// To get users based on given group..
	@GetMapping("userFindByGroup/{group}")
	public List<Users> userFindByGroup(@PathVariable String group) {
		List<Groups> list = groupRepo.findByGroupnameIgnoreCase(group);
		return userRepo.findBygroup(list.get(0));
	}

	// To upload fileName in db..
	@PostMapping("/fileUpload")
	public HttpStatus uploadFile(@RequestParam("file") MultipartFile fil) {


		return HttpStatus.CREATED;

	}

	@PostMapping(value = "/savefile", consumes = { "multipart/form-data" })
	public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {



		byte[] bytes = null;
		try {
			bytes = file.getBytes();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
		FileUpload fu = new FileUpload(file.getOriginalFilename(), file.getContentType(), bytes);
		fupRepo.save(fu);


		return null;
	}

	// To Upload weekly diet..
	@PostMapping("uploadWeeklyDiet")
	public HttpStatus uploadWeeklyDiet(@RequestBody WeeklyDiet diet) {
		DateAndTimeFinder dtm = new DateAndTimeFinder();
		diet.setDtm(dtm.getCurrentDateAndTime());
		wdRepo.save(diet);
		return HttpStatus.CREATED;
	}
	
	
	// To view recently uploaded weekly diet...
	@GetMapping("viewRecentUploads")
	public List<WeeklyDiet> viewRecentUploads() {
		return (List<WeeklyDiet>) wdRepo.findAll();
	}

	// To download the file based on file name...
	@GetMapping("downloadFile/{name}")
	public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String name) {

		
		List<FileUpload> fil = fupRepo.findByFilename(name);
		FileUpload f = fil.get(0);
		HttpHeaders headers = new HttpHeaders();
		headers.add("filename", f.getFilename());
		headers.add("filetype", f.getFiletype());
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + f.getFilename() + "\"");
		headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "filename,filetype," + HttpHeaders.CONTENT_DISPOSITION);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(f.getFiletype())).headers(headers)
				.body(new ByteArrayResource(f.getData()));

	}

	@ResponseBody
	public ResponseEntity dd(@PathVariable String name) throws IOException {

		
		List<FileUpload> dbFile = fupRepo.findByFilename(name);


		return ResponseEntity.ok().contentType(MediaType.parseMediaType(dbFile.get(0).getFiletype()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.get(0).getFilename() + "\"")
				.body(new ByteArrayResource(dbFile.get(0).getData()));

	}

	// To remove the file
	@PostMapping("/removeFile/{id}")
	public HttpStatus removeFile(@PathVariable int id) {
	
		if (fupRepo.existsById(id))
			fupRepo.deleteById(id);
		if (wdRepo.existsById(id))
			wdRepo.deleteById(id);

		return HttpStatus.CREATED;
	}

	// To check is user availbale with given id...
	@GetMapping("isUserAvailable/{id}")
	public boolean isUserAvailable(@PathVariable String id) {
		return userRepo.existsById(id);
	}
	// To check is motivator availbale with given id...
	@GetMapping("isMotivatorAvailable/{id}")
	public boolean isMotivatorAvailable(@PathVariable String id) {
		return motiRepo.existsById(id);
	}

	// To change password...
	@PostMapping("changePassword")
	public HttpStatus changePassword(@RequestBody LoginCredentials log) {
		logRepo.save(log);
		return HttpStatus.CREATED;
	}

	// To validate forgot password requests..

	@GetMapping("forgotPassword/{id}")
	public GeneratedCode forgotPassword(@PathVariable String id) {


		PasswordGenerator pg = new PasswordGenerator();
		String code = "";
		String TO = "";
		String SUB = "";
		String MSG = "";
		if (userRepo.existsById(id)) {

			String name = userRepo.findById(id).get().getName();
			Users u = userRepo.findById(id).get();
	
			code = pg.getRandomString(6);

			TO = u.getEmail();
			SUB = "Online Diet System || Password Reset ";
			MSG = "Hi " + name + " \n\n Plese enter this code to reset your password" + "\n\n Your 6 Digit code - "
					+ code + "\n If you have any queries means, Please reach us through onlineDietManagement@diet.com"
					+ "\n\n Thank You, \n Have a Healthy Life !";

		} else if (motiRepo.existsById(id)) {
			// Send Mail with 6 Digit Code..
			String name = motiRepo.findById(id).get().getName();
			code = pg.getRandomString(6);
			TO = id;
			SUB = "Online Diet System || Password Reset ";
			MSG = "Hi " + name + " \n\n Plese enter this code to reset your password" + "\n\n Your 6 Digit code - "
					+ code + "\n If you have any queries means, Please reach us through onlineDietManagement@diet.com"
					+ "\n\n Thank You, \n Have a Healthy Life !";

		}

		System.out.println();
		System.out.println("----------------------------------------------------");
		System.out.println("FORGOT PASSWORD REQ BY - "+id);
		
		System.out.println("Generated Code : "+code);

		System.out.println();
		System.out.println("Sending Mail.....");
		notification.sendNotification(TO, SUB, MSG);
		GeneratedCode gc = new GeneratedCode();
		gc.setCode(code);

		return gc;

	}
}
