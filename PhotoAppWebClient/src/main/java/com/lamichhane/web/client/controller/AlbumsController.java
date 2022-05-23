package com.lamichhane.web.client.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.lamichhane.web.client.response.AlbumRest;

@Controller
public class AlbumsController {
	
	@Autowired
	OAuth2AuthorizedClientService oauth2ClientService;
	
//	@Autowired
//	RestTemplate restTemplate;
	
	@Autowired
	WebClient webClient;

	@GetMapping("/albums")
	public String getAlbums(Model model, @AuthenticationPrincipal OidcUser principal) {
		
		
		String url = "http://localhost:8091/albums";
		List<AlbumRest> albums = webClient.get().uri(url).retrieve().
								bodyToMono(new ParameterizedTypeReference<List<AlbumRest>>(){}).block();
		model.addAttribute("albums",albums);
		return "albums";
		
		
		/* first method with rest template but with webclient is best
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
		OAuth2AuthorizedClient OAuth2AuthorizedClient = oauth2ClientService.loadAuthorizedClient(oauthToken.getAuthorizedClientRegistrationId(), oauthToken.getName());
		String jwtAccessToken = OAuth2AuthorizedClient.getAccessToken().getTokenValue();
	    
		System.out.println("Jwt Token ="+jwtAccessToken);
		System.out.println("Principal="+principal);
		
		OidcIdToken idToken = principal.getIdToken();
		String idTokenValue = idToken.getTokenValue();
		System.out.println("idTokenValue = "+ idTokenValue);
		
		String url = "http://localhost:8091/albums";
		HttpHeaders headers = new HttpHeaders(); 
		headers.add("Authorization", "Bearer "+jwtAccessToken);
		HttpEntity entity = new HttpEntity<>(headers);
		ResponseEntity<List<AlbumRest>> responseEntity = restTemplate.exchange(url, HttpMethod.GET,entity,new ParameterizedTypeReference<List<AlbumRest>>() {});
		List<AlbumRest> myList = responseEntity.getBody();
		System.out.println("My List = "+myList);
		AlbumRest album = new AlbumRest();
		album.setAlbumId("AlbumOne");
		album.setAlbumTitle("Albume one title");
		album.setAlbumUrl("http://localhost:8082/albums/1");
		
		AlbumRest album2 = new AlbumRest();
		album2.setAlbumId("AlbumTwo");
		album2.setAlbumTitle("Albume two title");
		album2.setAlbumUrl("http://localhost:8082/albums/2");
		
		model.addAttribute("albums",myList);
		
		return "albums";
		
		*/
	}
}
