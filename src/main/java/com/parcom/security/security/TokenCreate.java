package com.parcom.security.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.concurrent.TimeUnit;


public class TokenCreate extends TokenUtils {

	@FunctionalInterface
	interface DurationSetter {
		Date getExpirationDate(Date now);
	}

	private static final long DEFAULT_TOKEN_DURATION = 30L;


	static String createToken(UserDetailsPC userDetails)
	{
		return createToken(userDetails,now -> new Date(now.getTime() + TimeUnit.MINUTES.toMillis(DEFAULT_TOKEN_DURATION)));
	}

	public static String createToken(UserDetailsPC userDetails, Integer duration) {
		int lduration = (duration!=null)?duration:1;
		return createToken(userDetails,now -> new Date(now.getTime() + TimeUnit.DAYS.toMillis(lduration)));
	}

	private static String createToken(UserDetailsPC userDetails, DurationSetter durationSetter) {
		Date now = new Date();
		Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
		claims.put(JWT_USER, userDetails.getUsername());
		claims.put(JWT_ID_USER, userDetails.getId());
		claims.put(JWT_ID_GROUP, userDetails.getIdGroup());
		claims.put(JWT_AUTHORITIES, userDetails.getAuthoritiesStr());

		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(new Date())
				.setExpiration(durationSetter.getExpirationDate(now))
				.signWith(SignatureAlgorithm.HS512, MAGIC_KEY)
				.compact();
	}


}