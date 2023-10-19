package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Ingredient;
import com.example.demo.entity.Recipe;
import com.example.demo.entity.Recipe_Ingredient;
import com.example.demo.entity.Scrap;
import com.example.demo.entity.Step;
import com.example.demo.entity.Tag;
import com.example.demo.entity.User;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.repository.RecipeIngredientRepository;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.repository.ScrapRepository;
import com.example.demo.repository.StepRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RecipeService {

	@Autowired
	private RecipeRepository reciperepository;
	@Autowired
	private RecipeIngredientRepository recipeIngredientRepository;

	@Autowired
	private ScrapRepository scraprepository;

	@Autowired
	private IngredientRepository ingredientRepository;

	@Autowired
	private StepRepository stepRepository;

	public List<Recipe> findRecipesByIngredients(List<String> ingredientNames) {
		return reciperepository.findByRecipeIngredientsIngredientIngredientNameIn(ingredientNames);
	}

	public void createRecipe(Recipe recipe, List<String> ingredientNames, List<String> mensurations) {
		// Recipe newRecipe = new Recipe();

		List<Recipe_Ingredient> recipeIngredients = new ArrayList<>();
		for (int i = 0; i < ingredientNames.size(); i++) {
			String ingredientName = (i < ingredientNames.size()) ? ingredientNames.get(i) : null;
			String mensuration = (i < mensurations.size()) ? mensurations.get(i) : null;

			Ingredient existingIngredient = ingredientRepository.findByIngredientName(ingredientName);
			if (existingIngredient != null) {
				// 이미 존재하는 재료 정보인 경우, 기존 재료 정보를 사용
				Recipe_Ingredient recipeIngredient = new Recipe_Ingredient();
				recipeIngredient.setRecipe(recipe);
				recipeIngredient.setIngredient(existingIngredient);
				recipeIngredient.setMensuration(mensuration);
				recipeIngredients.add(recipeIngredient);
			} else {
				// 존재하지 않는 재료 정보인 경우, 새로운 재료 정보를 생성하여 저장
				Ingredient newIngredient = new Ingredient();
				newIngredient.setIngredient_name(ingredientName);
				ingredientRepository.save(newIngredient);

				Recipe_Ingredient recipeIngredient = new Recipe_Ingredient();
				recipeIngredient.setRecipe(recipe);
				recipeIngredient.setIngredient(newIngredient);
				recipeIngredient.setMensuration(mensuration);
				recipeIngredients.add(recipeIngredient);
			}
		}

		recipe.setRecipeIngredients(recipeIngredients);

	}

	public void createtag(Recipe recipe, List<String> tag) {
		List<Tag> tags = new ArrayList<>();

		for (int i = 0; i < tag.size(); i++) {
			Tag ntag = new Tag();
			ntag.setRecipe(recipe);
			String tagc = tag.get(i);
			// System.out.println(tagc);
			ntag.setContent(tagc);
			tags.add(ntag);
		}
		recipe.setTag(tags);
	}

	private void deleteImageFile(String fileName) {
		// 프로젝트 내의 이미지 파일이 저장된 디렉토리 경로
		String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/img/";

		// 삭제할 파일의 전체 경로
		String filePath = projectPath + fileName;

		File file = new File(filePath);

		if (file.exists()) {
			if (file.delete()) {
				System.out.println("파일 삭제 성공: " + fileName);
			} else {
				System.out.println("파일 삭제 실패: " + fileName);
			}
		} else {
			System.out.println("파일이 존재하지 않습니다: " + fileName);
		}
	}

	public void editStep1(Recipe recipe, List<String> SContent, List<String> Singtxt, List<String> Stooltxt,
			List<String> Stip, List<String> Scontroltxt, List<MultipartFile> file1) {

		List<Step> existingSteps = stepRepository.findByRecipe(recipe);
		System.out.println(existingSteps.size()+"sadassssssssssssssss");

		for (int i = 0; i < existingSteps.size(); i++) {
			Step step = existingSteps.get(i);
			
			String SContents = (i < SContent.size()) ? SContent.get(i) : "";
			String Singtxts = (i < Singtxt.size()) ? Singtxt.get(i) : "";
			String Stooltxts = (i < Stooltxt.size()) ? Stooltxt.get(i) : "";
			String Stips = (i < Stip.size()) ? Stip.get(i) : "";
			String Scontroltxts = (i < Scontroltxt.size()) ? Scontroltxt.get(i) : "";
			
			step.setSContent(SContents);
			step.setSingtxt(Singtxts);
			step.setStooltxt(Stooltxts);
			step.setStip(Stips);
			step.setScontroltxt(Scontroltxts);
			
			MultipartFile newImage = file1.get(i);
			if (newImage != null && !newImage.isEmpty())  {
				String newImagePath = uploadImage(newImage, null);

				step.setSphotopath(newImagePath);
			}else {
				step.setSphotopath(existingSteps.get(i).getSphotopath());
			}
			String existingImagePath = step.getSphoto(); // 기존 이미지 경로 가져오기

// 이미지 업로드를 처리하고 스텝의 이미지 경로를 업데이트합니다.
			String newImagePath = uploadImage(newImage, existingImagePath);

			step.setSphoto(newImagePath);
		}

// 새로운 스텝을 추가합니다.
		for (int i = existingSteps.size(); i < SContent.size(); i++) {
			Step newStep = new Step();
			String SContents = (i < SContent.size()) ? SContent.get(i) : "";
			String Singtxts = (i < Singtxt.size()) ? Singtxt.get(i) : "";
			String Stooltxts = (i < Stooltxt.size()) ? Stooltxt.get(i) : "";
			String Stips = (i < Stip.size()) ? Stip.get(i) : "";
			String Scontroltxts = (i < Scontroltxt.size()) ? Scontroltxt.get(i) : "";
			newStep.setRecipe(recipe);
			newStep.setSContent(SContents);
			newStep.setSingtxt(Singtxts);
			newStep.setStooltxt(Stooltxts);
			newStep.setStip(Stips);
			newStep.setScontroltxt(Scontroltxts);

			MultipartFile newImage = file1.get(i);
			
				String newImagePath = uploadImage(newImage, null);

				newStep.setSphotopath(newImagePath);
			

// 이미지 업로드를 처리하고 스텝의 이미지 경로를 설정합니다.
			

			existingSteps.add(newStep);
		}

// 필요에 따라 추가된 스텝을 삭제합니다.
		while (existingSteps.size() > SContent.size()) {
			Step removedStep = existingSteps.remove(existingSteps.size() - 1);
			stepRepository.delete(removedStep);
		}

// 레시피 내의 스텝을 업데이트합니다.
		recipe.setSteps(existingSteps);
		
	}

// 이미지 업로드를 처리하고 이미지 경로를 반환하는 메소드
	private String uploadImage(MultipartFile image, String existingImagePath) {
		if (image != null && !image.isEmpty()) {
	        try {
	            // 이미지를 저장할 디렉토리 경로 설정
	            String uploadDir = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img"; // 실제 디렉토리 경로로 설정해야 합니다.

	            // 원본 파일 이름
	            String originalFileName = image.getOriginalFilename();

	            // 저장할 파일 이름 생성 (고유한 이름으로 저장하는 것이 좋습니다)
	            String fileName = UUID.randomUUID().toString() + "_" + originalFileName;

	            // 저장할 파일 경로 생성
	            String filePath = "/img/"+ fileName;
	            String filePath1 = Paths.get(uploadDir, fileName).toString();

	            // 파일을 지정된 경로로 복사
	            Files.copy(image.getInputStream(), Paths.get(filePath1), StandardCopyOption.REPLACE_EXISTING);

	            // 새 이미지 파일의 경로를 반환
	            return filePath;
	        } catch (IOException e) {
	            // 이미지 업로드 중 오류가 발생한 경우 예외 처리
	            e.printStackTrace(); // 적절한 오류 처리를 수행하세요.
	        }
	    } else if (existingImagePath != null && !existingImagePath.isEmpty()) {
// 이미지 파일이 없는 경우, 기존 이미지 경로를 반환
			return existingImagePath;
		}
		return null; // 이미지 업로드에 실패하고 기존 이미지도 사용하지 않는 경우 null 반환
	}

	public void editStep(Recipe recipe, List<String> SContents, List<String> Singtxts, List<String> Stooltxts,
			List<String> Stips, List<String> Scontroltxts, List<MultipartFile> cookingStepImages, List<Step> steps) {

		// List<Step> steps = new ArrayList<>();
		List<String> photo = new ArrayList<>();
		List<String> photopath = new ArrayList<>();

		if (steps == null) {
			// steps가 null일 때 photo와 photopath에 빈 문자열을 추가
			for (int i = 0; i < SContents.size(); i++) {
				photo.add("");
				photopath.add("");
			}
		} else {
			for (int i = 0; i < steps.size(); i++) {
				photo.add(steps.get(i).getSphoto());
				photopath.add(steps.get(i).getSphotopath());
			}
		}
		int maxSteps = Math.max(steps.size(), SContents.size());
		for (int i = 0; i < maxSteps; i++) {

			if (i >= SContents.size()) {
				// SContent 목록보다 스텝이 더 많은 경우 기존 스텝을 삭제합니다.
				if (i < steps.size()) {
					Step step = steps.get(i);
					// 이미지 삭제
					String imageFileName = step.getSphoto();
					if (imageFileName != null && !imageFileName.isEmpty()) {
						deleteImageFile(imageFileName); // 이미지 파일을 삭제하는 메소드 호출
					}
					// 스텝 삭제
					stepRepository.delete(step);
					continue;
				}
			}

			Step step;
			if (i < steps.size()) {
				// 기존 스텝 업데이트
				step = steps.get(i);
			} else {
				// 새로운 스텝 추가
				step = new Step();
				step.setRecipe(recipe);
			}

			// 필드 업데이트
			step.setSContent(SContents.get(i));
			step.setSingtxt(Singtxts.get(i));
			step.setStooltxt(Stooltxts.get(i));
			step.setStip(Stips.get(i));
			step.setScontroltxt(Scontroltxts.get(i));

			MultipartFile stepImageFile = cookingStepImages.get(i);
			if (stepImageFile != null) {
				if (!stepImageFile.isEmpty()) {
					try {
						String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img";
						UUID uuid = UUID.randomUUID();
						String fileName = uuid + "_" + stepImageFile.getOriginalFilename();
						File saveFile = new File(projectPath, fileName);
						stepImageFile.transferTo(saveFile);
						step.setSphoto(fileName);
						step.setSphotopath("/img/" + fileName);
					} catch (Exception e) {
						// 파일 업로드 중 오류가 발생한 경우 예외 처리
						e.printStackTrace(); // 또는 로깅 등을 활용하여 오류를 기록할 수 있습니다.
					}
				} else {
					// 이미지가 비어있는 경우 이미지 필드를 비웁니다.
					// step.setSphoto(photo.get(i)); // 또는 다른 기본값을 설정할 수 있습니다.
					// step.setSphotopath(photopath.get(i));
				}
			} else if (step.getSphoto() == null || step.getSphoto().isEmpty()) {
				// 만약 업로드된 파일이 없고, 스텝의 이미지도 비어있는 경우,
				// 여기에 기존 이미지 경로를 설정하거나 다른 처리를 추가할 수 있습니다.
			}
			steps.add(step);
		}

		recipe.setSteps(steps);
	}

	public void createStep(Recipe recipe, List<String> SContents, List<String> Singtxts, List<String> Stooltxts,
			List<String> Stips, List<String> Scontroltxts, List<MultipartFile> cookingStepImages) {

		List<Step> steps = new ArrayList<>();

		int maxSize = Math.max(Math.max(Math.max(SContents.size(), Singtxts.size()), Stooltxts.size()),
				Math.max(Stips.size(), Scontroltxts.size()));

		for (int i = 0; i < maxSize; i++) {
			String SContent = (i < SContents.size()) ? SContents.get(i) : "";
			String Singtxt = (i < Singtxts.size()) ? Singtxts.get(i) : "";
			String Stooltxt = (i < Stooltxts.size()) ? Stooltxts.get(i) : "";
			String Stip = (i < Stips.size()) ? Stips.get(i) : "";
			String Scontroltxt = (i < Scontroltxts.size()) ? Scontroltxts.get(i) : "";

			// 각 리스트에서 요소가 존재하는지 확인
			if (SContent != null || Singtxt != null || Stooltxt != null || Stip != null || Scontroltxt != null
					|| (cookingStepImages != null && !cookingStepImages.get(i).isEmpty())) {
				Step step = new Step();
				step.setRecipe(recipe);
				step.setSContent(SContent);
				step.setScontroltxt(Scontroltxt);
				step.setSingtxt(Singtxt);
				step.setStip(Stip);
				step.setStooltxt(Stooltxt);

				if ((cookingStepImages != null && !cookingStepImages.get(i).isEmpty())) {
					try {
						String proijectpath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img"; // 단계
																														// 이미지
																														// 저장
																														// 경로
						MultipartFile stepImageFile = cookingStepImages.get(i);

						UUID uuid = UUID.randomUUID();
						String fileName = uuid + "_" + stepImageFile.getOriginalFilename();
						System.out.println(fileName);
						File savefile = new File(proijectpath, fileName);
						stepImageFile.transferTo(savefile);
						step.setSphoto(fileName);
						step.setSphotopath("/img/" + fileName);
					} catch (IOException e) {
						e.printStackTrace();
						// 이미지 저장 중 오류 발생 시 처리
					}
				} 
				steps.add(step);
			} else {
				// 처리할 수 없는 상황이라면 예외 처리 또는 오류 처리를 수행하세요.
				// 예를 들어, 로그를 남기거나 사용자에게 오류 메시지를 표시할 수 있습니다.
			}
		}

		recipe.setSteps(steps);
	}

	@Value("${upload.path}") // 파일 업로드 경로를 프로퍼티에서 읽어옵니다.
	private String uploadPath;

	public void write(Recipe recipe, MultipartFile file) throws Exception {
		String proijectpath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img";
		UUID uuid = UUID.randomUUID();
		String fileName = uuid + "_" + file.getOriginalFilename();
		File savefile = new File(proijectpath, fileName);
		file.transferTo(savefile);
		recipe.setMain_photo(fileName);
		recipe.setMain_photo_path("/img/" + fileName);

		// reciperepository.save(recipe);
	}

	public void editwrite(Recipe recipe, MultipartFile file, String Rphoto, String Rphotopath) throws Exception {
		if (file != null && !file.isEmpty()) {
			String proijectpath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img";
			UUID uuid = UUID.randomUUID();
			String fileName = uuid + "_" + file.getOriginalFilename();
			File savefile = new File(proijectpath, fileName);
			file.transferTo(savefile);
			recipe.setMain_photo(fileName);
			recipe.setMain_photo_path("/img/" + fileName);
		} else {
			recipe.setMain_photo(Rphoto);
			recipe.setMain_photo_path(Rphotopath);
		}
		// reciperepository.save(recipe);
	}

	public void userwrite(User user, MultipartFile file) throws Exception {
		String proijectpath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img";
		UUID uuid = UUID.randomUUID();
		String fileName = uuid + "_" + file.getOriginalFilename();
		File savefile = new File(proijectpath, fileName);
		file.transferTo(savefile);
		user.setUserphotopath("/img/" + fileName);

		// reciperepository.save(recipe);
	}

	public void stepwrite(Recipe recipe, MultipartFile file) throws Exception {
		String proijectpath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img";
		UUID uuid = UUID.randomUUID();
		String fileName = uuid + "_" + file.getOriginalFilename();
		File savefile = new File(proijectpath, fileName);
		file.transferTo(savefile);
		recipe.setMain_photo(fileName);
		recipe.setMain_photo_path("/img/" + fileName);

		// reciperepository.save(recipe);
	}

	public List<Recipe> getAllRecipes() {
		return reciperepository.findAll();
	}

	public Recipe getRecipeById(int id) {
		Optional<Recipe> optionalRecipe = Optional.ofNullable(reciperepository.findById(id));
		return optionalRecipe.orElse(null);
	}

	@Transactional
	public void deletePostWithImage(int recipe_id) {
		// 1. 게시물 정보 조회
		Recipe recipe = reciperepository.findById(recipe_id);

		List<Scrap> scraps = scraprepository.findByRecipe(recipe);

		if (recipe != null) {
			// 2. 이미지 파일 삭제
			String imagePath = recipe.getMain_photo_path();
			if (imagePath != null) {
				deleteImage(imagePath);
			}
		}

		// 3. 게시물 및 이미지 정보 삭제

		for (int i = 0; i < scraps.size(); i++) {

			scraprepository.delete(scraps.get(i));
		}

		reciperepository.delete(recipe);
	}

	private void deleteImage(String imagePath) {
		File imageFile = new File(imagePath);
		if (imageFile.exists()) {
			imageFile.delete();
		}
	}

	public Page<Recipe> getRecipesByPage(int pageNumber, int pageSize) {
		PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize); // 페이지 번호는 0부터 시작하므로 -1
		return reciperepository.findAll(pageRequest);
	}

}
