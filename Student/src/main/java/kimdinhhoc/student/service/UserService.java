package kimdinhhoc.student.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import kimdinhhoc.student.dto.PageDTO;
import kimdinhhoc.student.dto.SearchDTO;
import kimdinhhoc.student.dto.UserDTO;
import kimdinhhoc.student.entity.User;
import kimdinhhoc.student.repository.UserRepo;

//@Component
@Service // tao bean: new Uservice, qly SpringContainer
public class UserService implements UserDetailsService {

	@Autowired
	UserRepo userRepo;
	
	@Override
//	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userEntity = userRepo.findByUsername(username);
		if (userEntity == null) {
			throw new UsernameNotFoundException("Not Foud");
		}
		
		//convert userentity -> userdetails
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		//chuyen vai tro ve quyen
		for (String role : userEntity.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		
		return new org.springframework.security.core.
				userdetails.User(username, 
				userEntity.getPassword(), authorities);
	}

	@Transactional
	public void create(UserDTO userDTO) {
		User user = new ModelMapper().map(userDTO, User.class);
		//conver passwort to brcrypt
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		userRepo.save(user);
	}

	@Transactional
	public void delete(int id) {
		userRepo.deleteById(id);
	}

	@Transactional
	public void update(UserDTO userDTO) {
		// check
		User currentUser = userRepo.findById(userDTO.getId()).orElse(null);

		if (currentUser != null) {
			currentUser.setName(userDTO.getName());
			currentUser.setAge(userDTO.getAge());
			currentUser.setHomeAddress(userDTO.getHomeAddress());
			if (userDTO.getAvatarURL() != null)
				currentUser.setAvatarURL(userDTO.getAvatarURL());

			userRepo.save(currentUser);
		}
	}

	@Transactional
	public void updatePassword(UserDTO userDTO) {
		// check
		User currentUser = userRepo.findById(userDTO.getId()).orElse(null);
		if (currentUser != null) {
			currentUser.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));

			userRepo.save(currentUser);
		}
	}

	public UserDTO getById(int id) {
		// Optional
		User user = userRepo.findById(id).orElse(null);

		if (user != null) {
			return convert(user);
		}

		return null;
	}

	private UserDTO convert(User user) {
		return new ModelMapper().map(user, UserDTO.class);
	}

	public List<UserDTO> getAll() {
		List<User> userList = userRepo.findAll();

//		List<UserDTO> userDTOs = new ArrayList<>();
//		for (User user : userList) {
//			userDTOs.add(convert(user));
//		}
//
//		return userDTOs;
		// java 8
		return userList.stream().map(u -> convert(u))
				.collect(Collectors.toList());
	}

	//T: List<UserDTO>
	public PageDTO<List<UserDTO>> searchName(SearchDTO searchDTO) {
		Sort sortBy = Sort.by("name").ascending().and(Sort.by("age").descending());

		if (StringUtils.hasText(searchDTO.getSortedField())) {
			sortBy = Sort.by(searchDTO.getSortedField()).ascending();
		}

		if (searchDTO.getCurrentPage() == null)
			searchDTO.setCurrentPage(0);

		if (searchDTO.getSize() == null)
			searchDTO.setSize(5);

		PageRequest pageRequest = PageRequest.of(searchDTO.getCurrentPage(), searchDTO.getSize(), sortBy);

		Page<User> page = userRepo.searchByName("%" + searchDTO.getKeyword() + "%", pageRequest);

		// T: List<UserDTO>
		PageDTO<List<UserDTO>> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(page.getTotalPages());
		pageDTO.setTotalElements(page.getTotalElements());

		// List<User> users = page.getContent();
		List<UserDTO> userDTOs = page.get().map(u -> convert(u))
				.collect(Collectors.toList());
		
		// T: List<UserDTO>
		pageDTO.setData(userDTOs);

		return pageDTO;
	}
}
