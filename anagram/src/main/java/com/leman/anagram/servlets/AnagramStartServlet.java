package com.leman.anagram.servlets;

import static java.text.MessageFormat.format;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.emailvision.commons.api.restful.exceptions.entity.ExceptionEntity;
import com.emailvision.commons.properties.EmvProperties;
import com.emailvision.commons.properties.IEmvProperties;
import com.leman.core.api.dictionar.client.anagram.AnagramCoreApiClient;
import com.leman.core.api.dictionar.client.anagram.AnagramCoreApiException;
import com.leman.core.api.dictionar.common.anagram.entities.AnagramEntity;

public class AnagramStartServlet extends HttpServlet {
	
	private static final long serialVersionUID = 93312596895596119L;
    private static final Logger LOG = Logger.getLogger(AnagramStartServlet.class);
      
    public static final String PAGE_OGIN_CHANGE_PASSWORD = "/login/jsp/AnagramStart.jsp";
    
    private IEmvProperties properties;
    
    private static Integer timeout;
    private static Boolean debugMode;
    
    private static String dictionarApiHost;
    private static String dictionarApiUrlFormat;
    
    private static AnagramCoreApiClient anagramCoreApiClient;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        LOG.info(format("{0} initialized", getClass().getSimpleName()));
        
        properties = (IEmvProperties) config.getServletContext().getAttribute(EmvProperties.CONTEXT_PROPERTIES_ATTRIBUTE);

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
    public void destroy() {
    	anagramCoreApiClient.destroy();
    	super.destroy();
    }
    
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse resp) throws ServletException, IOException {
    	if (LOG.isDebugEnabled()) {
    		LOG.debug("Enternig doGet AnagramStartServlet");
    	}
    	final AnagramEntity anagramEntity;
    	
    	try {
			anagramEntity = anagramCoreApiClient.getWord(dictionarApiHost);
		} catch (AnagramCoreApiException e) {
			final ExceptionEntity entity = e.getEntity();
			LOG.error(entity.getErrorMessage());
			throw new ServletException(e);
		}

    	request.setAttribute("word", anagramEntity.getWord());
    	
    	final StringBuilder url = new StringBuilder("../anagram/jsp/AnagramStart.jsp");

    	if (LOG.isDebugEnabled()) {
    		LOG.debug(format("redirect url : \"{0}\"", url));
    	}
    	
    	resp.sendRedirect(url.toString());
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse resp) throws ServletException, IOException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Entering doPost AnagramStartServlet");
        }
        
//        final String login = getFirstHttpAttributeStringValue(request, AuthenticationServlet.HTTP_ATTR_USERNAME);
//        final String oneTimePassword = getFirstHttpAttributeStringValue(request, HTTP_ATTR_URLENCRYPTKEY);
//        final String newPassword = getFirstHttpAttributeStringValue(request, HTTP_ATTR_NEWPASSWORD);
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
