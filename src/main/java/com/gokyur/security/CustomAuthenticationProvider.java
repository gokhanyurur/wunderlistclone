package com.gokyur.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import com.gokyur.entity.Users;
import com.gokyur.service.UserService;
import com.gokyur.utilities.GokyurUtilities;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private UserService userService;
	
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        Users theUser = userService.getUser(username);
        
        
        if(theUser == null) {
        	throw new BadCredentialsException("Username not found.");
        }
        if(!GokyurUtilities.MD5(password).equals(theUser.getPassword())) {
        	throw new BadCredentialsException("Wrong password.");
        }

        if(!theUser.isActive()) {
        	throw new BadCredentialsException("Account is not activated.");
        }
        
        List<GrantedAuthority> role =AuthorityUtils.commaSeparatedStringToAuthorityList(theUser.getRole().getRole());
        
        return new UsernamePasswordAuthenticationToken(theUser.getUsername(), theUser.getPassword(), role);
	}

	public boolean supports(Class<?> authentication) {
		return authentication.equals(
		          UsernamePasswordAuthenticationToken.class);
	}

}
