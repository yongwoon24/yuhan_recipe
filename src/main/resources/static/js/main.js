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
		if(currentIdx<slideCount - 6){
			moveSlide(currentIdx + 1);
		}else{
			moveSlide(0);
		}
	});
	prevBtn.addEventListener('click', function(){
		if(currentIdx>0){
			moveSlide(currentIdx - 1);
		}else{
			moveSlide(slideCount - 6);
		}
		
	});