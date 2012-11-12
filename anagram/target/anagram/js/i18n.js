//sessvars.$.debug();
var i18n = {
	actionLink :  '',
	locale:'en-GB',
	bundle:'',
	keyList:new Array(),
	noValueList:new Array(),
	callbackList:new Array(),
	
	init: function(useLocale,applicationBundle,applicationActionLink)
	{
		this.locale=useLocale;		
		this.bundle=applicationBundle;
		this.actionLink=applicationActionLink;
		sessLocale=sessvars.locale;
		
		if(this.locale!=sessLocale){		
			this.keyList=new Object();
		}
		else{
			this.keyList=sessvars.keyList;			
		}					
		this.noValueList=new Array();		
	},
	
	addNeededKey: function(keyName){
		if (typeof this.keyList[keyName]=='undefined') {
			this.noValueList.push(keyName);
		}			
	},
	
	addNeededKeys: function(keyList){
		for( i=0;i<keyList.length;i++){
			this.addNeededKey(keyList[i]);
		}
	},
	
	getString:function(keyName,paramList){
		paramList = typeof paramList != 'undefined' ? paramList : new Array();
		value=this.keyList[keyName];
		for(i=0;i<paramList.length;i++){
			reg=new RegExp("\\{"+i+"\\}", "g");
			value=value.replace(reg,paramList[i]);
		}		
		
		return value;
	},
	
	getStringAsync:function(keyName,callback,paramList) {
		var self = this;
		if (typeof self.getString(keyName)== 'undefined'){			
			this.callbackList.push(
				function () {
					callback(self.getString(keyName,paramList));
				}
			);
		}else{		
			callback(self.getString(keyName,paramList));
		}
		
	},
		
	importValues:function() {	
		if(this.noValueList.length>0){				
		   	var self=this;		  
		   	
			$.ajax({
				type: "POST",
				url: this.actionLink,
				data: {keyList:this.noValueList,bundle:this.bundle, locale:this.locale},
				dataType: "json",
				success: function (data) {								
					for( i=0;i<self.noValueList.length;i++){	
						key=self.noValueList[i];												
						self.keyList[key]=data[key];	
					}						
							
					sessvars.keyList=self.keyList;								 					
 					sessvars.locale=self.locale;
 					self.noValueList=new Array();
 					var u;
 					while ( u = self.callbackList.shift()) u();
				}
				/*,
				error: function(){
					alert("Unable to perform asynchronous post request");
				},
				complete : function(XMLHttpRequest, textStatus) {
						info_message.empty();
						context.monitor.releaseChildLock();
				}*/
		
			});
		 }	
	}
	
};

