package com.leman.anagram.servlets;

import static java.text.MessageFormat.format;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leman.core.api.dictionar.client.anagram.AnagramCoreApiClient;
import com.leman.core.api.dictionar.client.anagram.AnagramCoreApiException;
import com.leman.core.api.dictionar.common.anagram.entities.WordEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import static com.emailvision.commons.http.utils.ParamChecker.getFirstHttpAttributeStringValue;

import com.emailvision.commons.api.restful.exceptions.entity.ExceptionEntity;
import com.emailvision.commons.properties.EmvProperties;
import com.emailvision.commons.properties.IEmvProperties;

@WebServlet("/AnagramStartServlet")
public class AnagramStartServlet extends HttpServlet {
	
	private static final long serialVersionUID = 93312596895596119L;
    private static final Logger LOG = Logger.getLogger(AnagramStartServlet.class);

    private IEmvProperties properties;
    
    private static Integer timeout;
    private static Boolean debugMode;
    
    private static String dictionarApiHost;
    private static String dictionarApiUrlFormat;
    
    private static AnagramCoreApiClient anagramCoreApiClient;
    
    private Set<WordEntity> anagramsEntities = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        LOG.info(format("{0} initialized", getClass().getSimpleName()));

        properties = (IEmvProperties) config.getServletContext().getAttribute(EmvProperties.CONTEXT_PROPERTIES_ATTRIBUTE);
        LOG.info(format("properties {0}", properties));

        timeout = properties.getIntProperty(AnagramCoreApiClient.PROPS_KEY_DICTIONAR_API_CLIENT_TIMEOUT, 30000);
        debugMode = properties.getBoolProperty(AnagramCoreApiClient.PROPS_KEY_DICTIONAR_API_CLIENT_DEBUG, false);
        
    	dictionarApiHost = properties.getProperty(AnagramCoreApiClient.PROPS_KEY_DICTIONAR_API_HOST);
    	dictionarApiUrlFormat = properties.getProperty(AnagramCoreApiClient.PROPS_KEY_DICTIONAR_API_SERVER_FORMAT);
    	
    	if (LOG.isInfoEnabled()) {
    		LOG.info(format(" timeout \"{0}\",  debugMode \"{1}\", dictionarApiHost \"{2}\", dictionarApiUrlFormat \"{3}\"", timeout, debugMode, 
    				dictionarApiHost, dictionarApiUrlFormat));
    	}
    	
    	anagramCoreApiClient = new AnagramCoreApiClient(dictionarApiUrlFormat, timeout, debugMode);
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
        LOG.info("doGet");
    	if (LOG.isDebugEnabled()) {
    		LOG.debug("Entering doGet AnagramStartServlet.....");
    	}
    	final WordEntity wordEntity;
    	
    	try {
			wordEntity = anagramCoreApiClient.getRandomWord(dictionarApiHost);
		} catch (AnagramCoreApiException e) {
			final ExceptionEntity entity = e.getEntity();
			LOG.error(entity.getErrorMessage());
			throw new ServletException(e);
		}

    	request.setAttribute("word", wordEntity.getWord());
    	request.setAttribute("wordEntity", wordEntity);
    	
    	if (LOG.isDebugEnabled()) {
    		LOG.debug("wordEntity.getWord(): " + wordEntity.getWord());
    	}
    	
    	final String url = new String("/jsp/AnagramStart.jsp");

    	if (LOG.isDebugEnabled()) {
    		LOG.debug(format("forward url : \"{0}\"", url));
    	}
    	
    	//dupa getContextPath urmeaza calea spre jsp; request.getContextPath() =/anagram
    	//apoi el compune /anagram/jsp/AnagramStart.jsp
    	request.getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("doPost");
        if (LOG.isDebugEnabled()) {
            LOG.debug("Entering doPost AnagramStartServlet.....");
        }
        
        final String typedWord = getFirstHttpAttributeStringValue(request, "text");
        final String word = getFirstHttpAttributeStringValue(request, "word");
        final String areDiacriticsPresent = getFirstHttpAttributeStringValue(request, "diacritics");
        final WordEntity wordEntity = (WordEntity) request.getAttribute("wordEntity");
        
        if (anagramsEntities == null) {
        	try {
				anagramsEntities = anagramCoreApiClient.getWordAnagrams(dictionarApiHost, wordEntity.getSortedWordChars(), Boolean.valueOf(areDiacriticsPresent));
			} catch (AnagramCoreApiException e) {
				final ExceptionEntity entity = e.getEntity();
				LOG.error(entity.getErrorMessage());
				throw new ServletException(e);
			}
        }
        
        boolean containsOnly2 = StringUtils.containsOnly(typedWord, wordEntity.getWord());

        if (LOG.isDebugEnabled()) {
    		LOG.debug("word: " + word);
    		LOG.debug("typedWord: " + typedWord);
    		LOG.debug("containsOnly2: " + containsOnly2);
    	}
    	
    	
//        final String confirmPassword = getFirstHttpAttributeStringValue(request, HTTP_ATTR_CONFIRMPASSWORD);
//
//        isBlankThrowIllegalArgumentException(login, AuthenticationServlet.HTTP_ATTR_USERNAME);
//        isBlankThrowIllegalArgumentException(oneTimePassword, HTTP_ATTR_URLENCRYPTKEY);
//        isBlankThrowIllegalArgumentException(newPassword, HTTP_ATTR_NEWPASSWORD);
//        isBlankThrowIllegalArgumentException(confirmPassword, HTTP_ATTR_CONFIRMPASSWORD);
//        
//        if (LOG.isDebugEnabled()) {
//            LOG.debug(format("Login: {0}, OTP:{1}, newPassword:*****, confirmPassword:*****", login, oneTimePassword));
//        }
//        
//        final ChangePasswordEntity changePasswordEntity = new ChangePasswordEntity(login, oneTimePassword, newPassword, confirmPassword);
//        final JSONObject json = new JSONObject();
//        
//        try {
//        	final Decoder decoder = new Decoder();
//        	decoder.setCcmdLoginTokenForBadb(oneTimePassword);
//            final Date connectionDate = decodeDate(decoder, DECODE_ATTR_CONNECTION_DATE);
//			if (! isValidDate(connectionDate)) {
//				json.put("e", "invalidDate");
//			}
//			
//			if (newPassword.equals(confirmPassword)) {
//				anagramCoreApiClient.changePasswordWithOneTimePassword(dictionarApiHost, changePasswordEntity);
//			} else {
//				json.put("e", "pwdDifferent");
//			}
//		} catch (final CryptoException e) {
//			LOG.error("Error while decoding the token", e);
//			LittleHoneyPot.paste();
//			throw new ServletException(e);
//		}  catch (final JSONException e) {
//			LOG.error("Error while adding key in json object", e);
//			throw new ServletException(e);
//		} catch (final OpenamCoreApiException e) {
//			LOG.error("Error while calling openam core - changePasswordWithOneTimePassword", e);
//			LittleHoneyPot.paste();
//			try {
//				final ExceptionEntity entity = e.getEntity();
//				if (entity != null) {
//					LOG.error(format(FORMAT_THE_OPENAM_CORE_RESPONSE, entity.getErrorMessage()));
//					if (entity.getErrorCode() != null && entity.getErrorCode().equals(ERROR_PASWWORD.getErrorCode().toString())) {
//						json.put("e", "weak");
//					} else {
//						json.put("e", "ko");
//					}
//				} else {
//					json.put("e", "ko");
//				}
//			} catch (final JSONException e1) {
//				LOG.error("Error while adding key in json object", e1);
//				throw new ServletException(e1);
//			}
//		} catch (final ClientHandlerException e) {
//			try {
//				json.put("e", "ko");
//			} catch (final JSONException e1) {
//				LOG.error("Error while adding key in json object", e1);
//				throw new ServletException(e1);
//			}
//			LOG.error("Error while calling openam core - changePasswordWithOneTimePassword ", e);
//			throw new ServletException(e);
//		} finally {
//			try {
//				final PrintWriter responseWriter = resp.getWriter();
//				json.write(responseWriter);
//				responseWriter.flush();
//				responseWriter.close();
//			} catch (final JSONException e) {
//				LOG.error("Error while write the json response", e);
//				throw new ServletException(e);
//			}
//		}    
    }
    
}
