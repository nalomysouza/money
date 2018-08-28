package com.example.moneyapi.config.token;

import com.example.moneyapi.config.user.UserInfoService;
import com.example.moneyapi.model.AutenticacaoOpenid;
import com.example.moneyapi.model.IdentificadorAutorizacao;
import com.example.moneyapi.model.Usuario;
import com.example.moneyapi.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * salvar os dados de identificação do usuário assim que o mesmo for autenticado
 * 
 * @author nalomysouza
 *
 */
@Service
@Transactional
public class OpenIdTokenServices {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ObjectMapper jsonMapper;

	@Autowired
	private UserInfoService userInfoService;
	//TODO: Melhorar esse metodo para salvar mais informações no banco de dados
	public void saveAccessToken(OAuth2AccessToken accessToken) {
		// acessando o endpoint userInfo do provedor
		Map<String, String> userInfo = userInfoService.getUserInfoFor(accessToken);

		TokenIdClaims tokenIdClaims = TokenIdClaims.extrairClaims(jsonMapper, accessToken);

		Optional<Usuario> usuarioAutenticado = usuarioRepository.buscarUsuarioAutenticado(new IdentificadorAutorizacao(tokenIdClaims.getSubjectIdentifier()));

		Usuario usuario = usuarioAutenticado.orElseGet(() -> {
			Usuario novoUsuario = new Usuario(userInfo.get("full_name"), userInfo.get("email"));

			new AutenticacaoOpenid(novoUsuario, new IdentificadorAutorizacao(tokenIdClaims.getSubjectIdentifier()),
					tokenIdClaims.getIssuerIdentifier(), obterDatetime(tokenIdClaims.getExpirationTime()));

			return novoUsuario;
		});

		// se a conta do usuario expirou, atualiza com a nova data de validade
		if (usuario.getAutenticacaoOpenid().expirou()) {
			AutenticacaoOpenid autenticacaoOpenid = usuario.getAutenticacaoOpenid();
			autenticacaoOpenid.setValidade(obterDatetime(tokenIdClaims.getExpirationTime()));
		}

		// acessando o endpoint userInfo
		String nomeDoUsuario = userInfo.get("name");

		usuario.alterarNome(nomeDoUsuario);

		usuarioRepository.registrar(usuario);

	}

	private Date obterDatetime(long timestamp) {
		return new Date(timestamp * 1000);
	}

}
