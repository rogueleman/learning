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

import org.apache.log4j.Logger;

import com.emailvision.commons.api.restful.exceptions.entity.ExceptionEntity;
import com.emailvision.commons.properties.EmvProperties;
import com.emailvision.commons.properties.IEmvProperties;
import com.leman.core.api.dictionar.client.anagram.AnagramCoreApiClient;
import com.leman.core.api.dictionar.client.anagram.AnagramCoreApiException;
import com.leman.core.api.dictionar.common.anagram.entities.WordEntity;

@WebServlet("/GetWordsFromDictionaryServlet")
public class GetWordsFromDictionaryServlet extends HttpServlet {
	
	private static final long serialVersionUID = -4615191511064746200L;

	private static final Logger LOG = Logger.getLogger(GetWordsFromDictionaryServlet.class);
      
    private IEmvProperties properties;
    
    private static Integer timeout;
    private static Boolean debugMode;
    
    private static String dictionarApiHost;
    private static String dictionarApiUrlFormat;
    
    private static AnagramCoreApiClient anagramCoreApiClient;
    
    private Set<WordEntity> wordsEntities = null;
    
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
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    	if (LOG.isDebugEnabled()) {
    		LOG.debug("Entering doGet GetWordsFromDictionaryServlet.....");
    	}
    	
    	final String url = new String("/jsp/GetWordsFromDictionary.jsp");

    	if (LOG.isDebugEnabled()) {
    		LOG.debug(format("forward url : \"{0}\"", url));
    	}
    	
    	//dupa getContextPath urmeaza calea spre jsp; request.getContextPath() =/anagram
    	//apoi el compune /anagram/jsp/AnagramStart.jsp
    	request.getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse resp) throws ServletException, IOException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Entering doPost GetWordsFromDictionaryServlet.....");
        }
        
        try {
			wordsEntities = anagramCoreApiClient.getWordsFromDefinition(dictionarApiHost, "acas");
		} catch (AnagramCoreApiException e) {
			final ExceptionEntity entity = e.getEntity();
			LOG.error(entity.getErrorMessage());
			throw new ServletException(e);
		}
        
        if (LOG.isDebugEnabled()) {
        	LOG.debug("wordsEntities.size(): " + wordsEntities.size());
        	LOG.debug("wordsEntities: " + wordsEntities);
        }
        
        for (WordEntity wordEntity : wordsEntities){
        	try {
				anagramCoreApiClient.postWord(dictionarApiHost, wordEntity.getWord(), "1");
			} catch (AnagramCoreApiException e) {
				final ExceptionEntity entity = e.getEntity();
				LOG.error(entity.getErrorMessage());
				throw new ServletException(e);
			}
        }
        
//        final String typedWord = getFirstHttpAttributeStringValue(request, "text");
//        final String word = getFirstHttpAttributeStringValue(request, "word");
//        final String areDiacriticsPresent = getFirstHttpAttributeStringValue(request, "diacritics");
//        final WordEntity wordEntity = (WordEntity) request.getAttribute("wordEntity");
//        
//        if (wordsEntities == null) {
//        	try {
//				wordsEntities = anagramCoreApiClient.getWordAnagrams(dictionarApiHost, wordEntity.getSortedWordChars(), Boolean.valueOf(areDiacriticsPresent));
//			} catch (AnagramCoreApiException e) {
//				final ExceptionEntity entity = e.getEntity();
//				LOG.error(entity.getErrorMessage());
//				throw new ServletException(e);
//			}
//        }
//        
//        boolean containsOnly2 = StringUtils.containsOnly(typedWord, wordEntity.getWord());
//
//        if (LOG.isDebugEnabled()) {
//    		LOG.debug("word: " + word);
//    		LOG.debug("typedWord: " + typedWord);
//    		LOG.debug("containsOnly2: " + containsOnly2);
//    	}

    }
    
}
