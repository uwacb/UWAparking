function check(){
	var target_form= document.getElementById("new_parking");
	for (var i=0; i<target_form.length;i++){
	   var element=target_form[i];
	   if (element.value!=""){
	       console.log("test");
	       
	       break;


	   }

	   else{
	   	target_form.submit();
	   }
	}
}	