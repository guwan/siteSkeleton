//package com.guwan.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import com.guwan.domain.User;
//import com.guwan.pojo.UserDetailsImpl;
//import com.guwan.repository.UserRepository;
//
//public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService{
//
//	@Autowired
//	UserRepository userRepository;
//	@Override
//	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//		User user =userRepository.loadUserByUsername(email);
//		UserDetailsImpl userDetailsImpl =new UserDetailsImpl();
//		copyUserToUserDetails(user,userDetailsImpl);
//		return userDetailsImpl;
//	}
//	private void copyUserToUserDetails(User user, UserDetailsImpl userDetailsImpl) {
//		userDetailsImpl.setId(user.getId());
//		userDetailsImpl.setAccountNonExpired(user.isAccountNonExpired());
//		userDetailsImpl.setAccountNonLocked(user.isAccountNonLocked());
//		userDetailsImpl.setAvatarUrl(user.getAvatarUrl());
//		userDetailsImpl.setBio(user.getBio());
//		userDetailsImpl.setCreatedAt(user.getCreatedAt());
//		userDetailsImpl.setCredentialsNonExpired(user.isCredentialsNonExpired());
//		userDetailsImpl.setEmail(user.getEmail());
//		userDetailsImpl.setGeoLocation(user.getGeoLocation());
//		userDetailsImpl.setIntegral(user.getIntegral());
//		userDetailsImpl.setJobTitle(user.getJobTitle());
//		userDetailsImpl.setLevelName(user.getLevelName());
//		userDetailsImpl.setLocation(user.getLocation());
//		userDetailsImpl.setUsername(user.getUsername());
//		userDetailsImpl.setPassword(user.getPassword());
//		userDetailsImpl.setUpdateAt(user.getUpdateAt());
//		userDetailsImpl.setUserEnabled(user.isEnabled());
//		userDetailsImpl.setVideoEmbeds(user.getVideoEmbeds());
//		
//	}
//
//}
