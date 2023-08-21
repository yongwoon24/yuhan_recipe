var img_slide = document.querySelector('.img_slide'),
	slide = document.querySelectorAll('.img_slide li'),
	currentIdx = 0,
	slideCount = slide.length,
	slideWidth = 400,
	slideMargin = 30,
	prevBtn = document.querySelector('.prev'),
	nextBtn = document.querySelector('.next');
	
	img_slide.style.width = (slideWidth+slideMargin)*slideCount - slideMargin + 30+'px';
	
	
	function moveSlide(num){
		img_slide.style.left = -num * 430 + 'px';
		currentIdx = num;
	}
	
	nextBtn.addEventListener('click', function(){
		if(currentIdx<slideCount - 15){
			moveSlide(currentIdx + 1);
		}else{
			moveSlide(0);
		}
	});
	prevBtn.addEventListener('click', function(){
		if(currentIdx>0){
			moveSlide(currentIdx - 1);
		}else{
			moveSlide(slideCount - 15);
		}
		
	});

	var ing = new Array();
    var cat = new Array();
    function init(){
   
       var tags = document.getElementById("t1");
       var btns=document.createElement("input");
       btns.type="button";
       btns.value=tags.value+"      x";
       btns.id=tags.value;
       ing.push(tags.value);
       alert(ing);
       tags.value="";
       btns.style.cursor="hand";
       btns.style.width="80px";
       btns.width="120px";          
       btns.color="white";
       btns.style.cursor="hand";
       btns.onclick=function(){hideInfo(this)};

       var clas = document.getElementById("body_wrapper3");
       clas.appendChild(btns);
                
     }

     function hideInfo(e){
         alert("삭제됨");
         var i = 0;
         for (i = 0; i < ing.length-1; i++) {
             if(ing[i]==e.id){
                 break;
             }
         }
         ing.splice(i,1);
         alert(ing);
         e.remove();    
     }
	
	
