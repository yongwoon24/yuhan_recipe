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