package com.niit.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.niit.dao.CoverPictureDao;
import com.niit.model.Error;
import com.niit.model.CoverPicture;
import com.niit.model.Users;

@Controller
public class CoverPictureController {
	@Autowired
private CoverPictureDao coverPictureDao;
	@RequestMapping(value="/uploadprofilepicture",method=RequestMethod.POST)
public ResponseEntity<?> uploadProfilePicture(@RequestParam CommonsMultipartFile image,HttpSession session){
	Users users=(Users)session.getAttribute("user");
	if(users==null)		{
		    Error error=new Error(3,"UnAuthorized user");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
	} 
	CoverPicture profilePicture=new CoverPicture();
	profilePicture.setUsername(users.getUsername());
	profilePicture.setImage(image.getBytes());
	coverPictureDao.saveCoverPicture(profilePicture);

	return new ResponseEntity<Users>(users,HttpStatus.OK);
}
		
	@RequestMapping(value="/setimage/{username}", method=RequestMethod.GET)
	public @ResponseBody byte[] getProfilePic(@PathVariable String username,HttpSession session){
		Users user=(Users)session.getAttribute("user");
		if(user==null)
			return null;
		else
		{
			CoverPicture coverPic=coverPictureDao.getCoverPic(username);
			if(coverPic==null)
				return null;
			else
				return coverPic.getImage();
		}
		
}
}
