document.addEventListener("DOMContentLoaded", function() {
    const categoryButtons = document.querySelectorAll(".category-button");
    const recipeList = document.getElementById("recipe-list");

    loadAllRecipes(); // 페이지 로드 시 모든 레시피 데이터 로드

    categoryButtons.forEach(button => {
        button.addEventListener("click", function() {
            const selectedCategory = button.getAttribute("data-category");
            loadRecipes(selectedCategory);
        });
    });

    function loadAllRecipes() {
        loadRecipes(null); // 카테고리 선택 없이 모든 레시피 데이터 로드
    }

    function loadRecipes(category) {
        const url = category ? "/?category_name=" + category : "/"; // 카테고리 선택 여부에 따라 URL 설정
        fetch(url)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                console.log(data); // 서버 응답 데이터를 콘솔로 출력
                recipeList.innerHTML = ""; // 기존 목록 초기화
                data.forEach(recipe => {
                    const li = document.createElement("li");
                    li.innerHTML = `
                        <a href="recipe"><img src="img/${recipe.image}" alt="Recipe Image"></a>
                        <a href="recipe">${recipe.title}</a>
                        <span>Likes: ${recipe.likes}</span>
                    `;
                    recipeList.appendChild(li);
                });
            })
            .catch(error => console.error("Error loading recipes:", error));
    }
});


document.getElementById('categoryName').addEventListener('change', function() {
        this.form.submit(); // 폼을 제출하여 선택한 카테고리에 대한 처리를 실행합니다.
    });