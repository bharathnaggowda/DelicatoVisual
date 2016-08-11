
    $().ready(function(){
    
    	$("#form_signup").validate({
    	
    	rules:{
    		firstname:"required",
    		lastname:"required",
    		password:{
    			
    			required:true,
    			minlength:6
    		},
    		
    		password2:{
    			required:true,
    			minlength:6,
    			equalTo:"#password"
    		},
    		email:{
    			required:true,
    			email:true
    		}
    		
    	},
    	
    	messages:{
    		firstname:"Firstname is required!",
    		lastname:"Lastname is required!",
    		email:"Email is required!",	
    		password:{
    			required:"Please provide password.",
    			minlength:"Your password must be at least 5 characters long."
    		},
    		password2:{
    			required:"Please provide password.",
    			minlength:"Your password must be at least 5 characters long.",
    			equalTo:"Password does not match."
    			
    		}
    	
    		
    	}
    	
    });
    	
    })( jQuery );

    
    
    
    
