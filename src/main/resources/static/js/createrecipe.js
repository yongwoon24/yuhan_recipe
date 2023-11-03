const addButton = document.getElementById('addButton');
const container = document.getElementById('container');

const removebutton1 = document.getElementById('removebutton');
const removebutton2 = document.getElementById('removebutton1');
const removebutton3 = document.getElementById('removebutton2');
const t1 = document.getElementById('t1');
const t2 = document.getElementById('t2');
const t3 = document.getElementById('t3');
const e10_2_1d = document.querySelector('.e10_2_1');
const e10_2_2d = document.querySelector('.e10_2_2');
const e10_2_3d = document.querySelector('.e10_2_3');
const tr = document.querySelector('text-row');
let itemCount = 0;
const items = ['예 ) 감 자', '예 ) 소 금', '예 ) 돼 지 고 기', '예 ) 간 장', '예 ) 우 유'];
const info = ['예 ) 3 개', '예 ) 1 T', '예 ) 3 0 0 g', '예 ) 한 큰 술', '예 ) 5 0 0 ml'];

addButton.addEventListener('click', () => {
	itemCount++;
	if (itemCount == 5) {
		itemCount = 0;
	}

	const textRow = document.createElement('div');
	textRow.classList.add('text-row');

	const text1 = document.createElement('input');
	text1.classList.add('text');
	text1.name="ingredientName";
	text1.textContent = '';
	text1.style.width = '200px';
	text1.style.height = '50px';
	text1.placeholder = items[itemCount];
	textRow.appendChild(text1);

	const text2 = document.createElement('input');
	text2.classList.add('text');
	text2.name="mensuration";
	text2.textContent = '';
	text2.style.width = '200px';
	text2.style.height = '50px';
	text2.placeholder = info[itemCount];
	textRow.appendChild(text2);

	const removeButton = document.createElement('button');
	removeButton.classList.add('remove-button');
	removeButton.textContent = '삭제';
	removeButton.addEventListener('click', () => {
		container.removeChild(textRow);
		adjustContainerHeight();
	});
	textRow.appendChild(removeButton);


	container.appendChild(textRow);
	adjustContainerHeight();
});
removebutton1.addEventListener('click', () => {
	container.removeChild(t1);
	adjustContainerHeight();
});
removebutton2.addEventListener('click', () => {
	container.removeChild(t2);
	adjustContainerHeight();
}); removebutton3.addEventListener('click', () => {
	container.removeChild(t3);
	adjustContainerHeight();
});





//요리스텝 스크립트
const container2 = document.getElementById('container1');
let stepCount = 1;
let steptxtindex = 1;
const steptext = ['예 ) 다 볶았으면 접시에 옮겨 담고 마지막으로 참깨가루를 뿌려주세요.',
	'예 ) 김치 반 포기를 잘게 썰어주세요.',
	'예 ) 양파, 대파을 썰고, 마늘은 다져주세요.',
	'예 ) 프라이팬에 식용유를 2스푼정도 두르고 중불로 달궈주세요.',
	'예 ) 김치와 양파를 넣고 5분정도 볶아주세요.',
	'예 ) 볶았으면 밥 한 공기와, 다시다 1큰술, 물엿 2큰술을 넣고 5분정도 볶아주세요.',
	'예 ) 볶은 뒤 참기름을 반큰술 넣고 1분만 볶아주세요.']

const addButton1 = document.getElementById("addButton1");


addButton1.addEventListener("click", () => {

    
	stepCount++;
	steptxtindex++;
	if (steptxtindex == 7) {
		steptxtindex = 0;
	}

	const newStep = document.createElement("div");
	newStep.className = "e56_212";
	newStep.id = "r" + stepCount;

	newStep.innerHTML = `
               <label class="step">STEP ${stepCount}</label><br>
								<div class="e56_213"><input type="button" class="remove-button1"
										onclick="removeStep1('r${stepCount}')" value="삭제"></div>
								<div class="e56_214">
									<input multiple type="file" id="file${stepCount}" name="file1" style="display: none;">
									<button id="uploadButton${stepCount}" name="uploadButton1" onclick="handleClick(event); handleUploadClick(${stepCount})">
										<div id="preview${stepCount}" name="preview1">
											<img src="/img/대표이미지.PNG" style="width: 363px; height: 234px;">
										</div>
								</div>
								<div class="e56_215"><textarea class='text2' name="SContent" placeholder="${steptext[steptxtindex]}" required></textarea></div>
								<div class="e56_216"><input type="text" class="text3" name="Singtxt"
									placeholder="재료에 대해 적어주세요! 예 ) 감자 1개, 소금 2큰술">
									</div>
								<div class="e56_217"><input type="text" class="text3" name="Stooltxt"
									placeholder="재료도구에 대해 적어주세요! 예 ) 냄비, 프라이팬, 국자"></div>
								<div class="e56_218"><input type="text" class="text3" name="Scontroltxt"
									placeholder="불 세기나 도구 사용시간에 대해 적어주세요! 예 ) 약불"></div>
								<div class="e56_219"><input type="text" class="text3" name="Stip"
									placeholder="팁이 있다면 적어주세요! 예 ) 삶은 감자를 2분 정도 식혀주세요"></div>
								<div class="e56_220"><img src="/img/carrot.png" style="width: 100%; height: 100%;"></div>
								<div class="e56_221"><img src="/img/냄비.png" style="width: 100%; height: 100%;"></div>
								<div class="e56_222"><img src="/img/불.png" style="width: 100%; height: 100%;"></div>
								<div class="e56_223"><img src="/img/전구.png" style="width: 100%; height: 100%;"></div>
            `;

	container2.appendChild(newStep);

	
	
});

function removeStep1(stepId) {
	const stepToRemove = document.getElementById(stepId);
	if (stepToRemove) {
		stepToRemove.remove();
		stepCount--;
		// 중간의 스텝이 제거되면 다시 스텝 숫자를 정렬
		reorganizeStepLabels();
		
	}
}


function reorganizeStepLabels() {
	const stepLabels = document.querySelectorAll(".step");
	stepLabels.forEach((label, index) => {
		label.textContent = `STEP ${index + 1}`;
	});
}


let stepnum2=0;
function handleUploadClick(stepNumber) {
	const imageInput1 = document.getElementById(`file${stepNumber}`);
	const uploadButton1 = document.getElementById(`uploadButton${stepNumber}`);
	const preview1 = document.getElementById(`preview${stepNumber}`);

	uploadButton1.addEventListener('click', () => {
		imageInput1.click();
	});



	imageInput1.addEventListener('change', (event) => {
		stepnum2=stepNumber
		var file1 = event.target.files[0];
		if (file1) {
			const reader = new FileReader();

			reader.onload = (e) => {
				const image1 = new Image();
				image1.src = e.target.result;
				image1.style.maxWidth = '363px';
				image1.style.maxHeight = '234px';
				image1.style.minWidth='363px';
				image1.style.minHeight = '234px';
				preview1.innerHTML = '';
				preview1.appendChild(image1);
			};

			reader.readAsDataURL(file1);
		}
	});

	
}



const imageInput = document.getElementById('file');
const uploadButton = document.getElementById('uploadButton');
const preview = document.getElementById('preview');

uploadButton.addEventListener('click', () => {
	imageInput.click();
});

imageInput.addEventListener('change', (event) => {
	const file = event.target.files[0];
	if (file) {
		const reader = new FileReader();

		reader.onload = (e) => {
			const image = new Image();
			image.src = e.target.result;
			image.style.maxWidth = '360px';
			image.style.maxHeight = '234px';
			image.style.minWidth='360px';
			image.style.minHeight = '234px';
			preview.innerHTML = '';
			preview.appendChild(image);
		};

		reader.readAsDataURL(file);
	}
});

function createListItem(text) {
            const listItem = document.createElement("div");
            listItem.className = "list-item";

            const inputHidden = document.createElement("input");
            inputHidden.type = "hidden";
            inputHidden.name = "tags";
            inputHidden.id = "tags";
            inputHidden.value = text;

            const deleteButton = document.createElement("button");
            deleteButton.className = "delete-button";
            deleteButton.textContent = text+"          X"; // 버튼 내용을 입력값(text)으로 설정
            deleteButton.onclick = function() {
                listItem.remove();
            };

           
            listItem.appendChild(deleteButton);
            listItem.appendChild(inputHidden);
            return listItem;
        }

        function handleInput(event) {
            const textInput = document.getElementById("textInput");
            const textList = document.getElementById("textList");
            if (event.key === "Enter") {
                event.preventDefault();
                const text = textInput.value;

                if (text.trim() !== "") {
                    const textArray = text.split(",");

                    textArray.forEach(item => {
                        const listItem = createListItem(item);
                        textList.appendChild(listItem);
                        textInput.value = "";
                    });
                }
            }
        }