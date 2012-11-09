package com.leman.anagram;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum Language {
	
	us(0, "English (US)", "en", "US", false, false),
	ro(1, "Romana", "ro", "RO", false, false),
	en(2, "English (UK)", "en", "GB", false, false),
	fr(3, "Français", "fr", "FR", false, false),
	de(4, "Deutsch", "de", "DE", false, false),
	nl(5, "Nederlands", "nl", "NL", false, false),       
	es(6, "Español", "es", "ES", false, false);
//	ru(7, "Русский", "ru", "RU", false, false),
//	sv(8, "Svenska", "sv", "SE", false, false),
//	it(9, "Italiano", "it", "IT", false, false),
//	cn(10, "中文 (简体)", "zh", "CN", false, false),
//	tw(11, "中文 (繁体)", "zh", "TW", false, false),
//	pt(12, "Português (Portugal)", "pt", "PT", false, false),
//	br(13, "Português (Brasil)", "pt", "BR", false, false),
//	da(14, "Dansk", "da", "DK", false, false),
//	ja(15, "日本語", "ja", "JP", false, false),
//	ko(16, "한국어", "ko", "KR", true, false),
//	he(17, "עברית", "he", "IL", false, true);
    
	public static Language getByValue(int id) {
		for (Language t : Language.values())
			if (t.getValue() == id)
				return t;
		return null;
	}
	
	private final String traduction;
	private final int value;
	private final String lang;
	private final String country;
	private final boolean betaVersion;
	private final boolean alphaVersion;
	
	private Language(int value, String traduction, String lang, String country, boolean betaVersion, boolean alphaVersion) {
		this.value = value;
		this.traduction = traduction;
		this.lang = lang;
		this.country = country;
		this.betaVersion = betaVersion;
		this.alphaVersion = alphaVersion;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getTraduction() {
		return traduction;
	}
	
	public String getLocaleId(){
		return this.lang+"_"+this.country;
	}
	
	public String getLocale(){
		return this.name();
		//return getLocaleISO(); //TODO if really locale in cookie
	}
	
	public String getLocaleISO(){
		return this.lang+"-"+this.country;
	}
	
	public String getLang(){
		return this.lang;
	}
	
	public String getCountry(){
		return this.country;
	}
	
	public boolean isBetaVersion(){
		return this.betaVersion;
	}
	
	public boolean isAlphaVersion(){
		return this.alphaVersion;
	}
	
	public static Language getDefault(){
		return Language.en;
	}

	public static Language getI18NLocale(String name){
		return getI18NLocale(name, false);
	}
	
	public static Language getI18NLocale(String name, boolean ISOValue){
		if(name == null || name.length() == 0){
			return getDefault();
		}
		for(Language i18nLocale : Language.values()){
			if(ISOValue && (i18nLocale.getLocaleISO().equalsIgnoreCase(name) || i18nLocale.getLocaleId().equalsIgnoreCase(name)) 
					|| !ISOValue && i18nLocale.getLocale().equalsIgnoreCase(name)){
				return i18nLocale;
			}
		}
		return getDefault();
	}
	
	public static boolean isExist(String name, boolean ISOValue){
		if(name == null || name.length() == 0){
			return false;
		}
		for(Language i18nLocale : Language.values()){
			String locale = ISOValue ? i18nLocale.getLocaleISO() : i18nLocale.getLocale();
			if(locale.equalsIgnoreCase(name)){
				return true;
			}
		}
		return false;
	}
	
	//TODO compare with Collator for alphabetic order with Locale
	public static Language[] alphabeticOrderValues(){
		List<Language> list = Arrays.asList(Language.values());
		Collections.sort(list, new ComparatorI18NLocale());
		return (Language[])list.toArray();
	}
	
	public static class ComparatorI18NLocale implements Comparator<Language>{
		
		public int compare(Language o1, Language o2) {
			return o1.getTraduction().compareTo(o2.getTraduction());
		}
	}
	
	public static Language[] getValues(){
		Language[] i18nLocales = new Language[NB_LANG_WITHOUT_ALPHA_VERSION];
		int i = 0;
		for(Language i18nLocale : Language.alphabeticOrderValues()){
			if(!i18nLocale.alphaVersion){
				i18nLocales[i++] = i18nLocale;
			}
		}
		return i18nLocales;
	}
	private static int NB_LANG_WITHOUT_ALPHA_VERSION = 7;
}
