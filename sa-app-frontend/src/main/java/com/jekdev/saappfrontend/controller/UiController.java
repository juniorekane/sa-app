package com.jekdev.saappfrontend.controller;

import com.jekdev.saappfrontend.dto.ClientRequest;
import com.jekdev.saappfrontend.dto.EmotionRequest;
import com.jekdev.saappfrontend.service.SentimentApiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Web controller for frontend page routing and form submissions.
 *
 * <p>This controller provides endpoints for client and emotion workflows and populates Thymeleaf views with the
 * required model attributes.
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(value = UiController.BASE_PATH)
public class UiController {

  public static final String BASE_PATH = "/ui";

  public static final String CLIENT_PATH = "/client";
  public static final String EMOTION_PATH = "/emotions";

  private final SentimentApiService api;

  /**
   * Displays the frontend entry page.
   *
   * @param model Spring MVC model
   * @return index template name
   */
  @GetMapping(value = CLIENT_PATH)
  public String getBasePath(Model model) {
    model.addAttribute("clientRequest", new ClientRequest());
    return "index";
  }

  /**
   * Handles client creation from the entry page.
   *
   * @param req client request payload
   * @param br validation result
   * @param model Spring MVC model
   * @return index template when successful or when validation fails
   */
  @PostMapping("/new/client")
  public String createClient(@Valid @ModelAttribute("clientRequest") ClientRequest req, BindingResult br, Model model) {
    if (br.hasErrors()) return "index";

    var status = api.createUser(req);
    log.info("API /api/client/create status = {}", status);

    model.addAttribute("status", status);
    return "index";
  }

  /**
   * Displays the page containing all clients.
   *
   * @param model Spring MVC model
   * @return listUser template name
   */
  @GetMapping(CLIENT_PATH + "/all")
  public String getAllClient(Model model) {
    model.addAttribute("clients", api.getAllClient());
    return "listUser";
  }

  /**
   * Displays a page for a single client.
   *
   * @param id client ID
   * @param model Spring MVC model
   * @return singleUser template name
   */
  @GetMapping(CLIENT_PATH + "/find")
  public String getOneClient(@RequestParam("id") Long id, Model model) {
    model.addAttribute("client", api.findSingleClient(id));
    return "singleUser";
  }

  /**
   * Displays the emotion creation page.
   *
   * @param model Spring MVC model
   * @return createSentiment template name
   */
  @GetMapping(EMOTION_PATH)
  public String getCreateEmotionPage(Model model) {
    if (!model.containsAttribute("emotionRequest")) {
      model.addAttribute("emotionRequest", newEmotionRequest());
    }
    return "createSentiment";
  }

  /**
   * Handles emotion creation requests.
   *
   * @param req emotion request payload
   * @param br validation result
   * @param model Spring MVC model
   * @return createSentiment template name
   */
  @PostMapping(EMOTION_PATH + "/create")
  public String createEmotion(
      @Valid @ModelAttribute("emotionRequest") EmotionRequest req, BindingResult br, Model model) {
    ensureClient(req);
    if (br.hasErrors()) return "createSentiment";

    var status = api.createEmotion(req);
    model.addAttribute("status", status);
    model.addAttribute("emotionRequest", newEmotionRequest());
    return "createSentiment";
  }

  /**
   * Displays all emotions.
   *
   * @param model Spring MVC model
   * @return listEmotion template name
   */
  @GetMapping(EMOTION_PATH + "/all")
  public String getAllEmotion(Model model) {
    model.addAttribute("emotions", api.getAllEmotions());
    return "listEmotion";
  }

  /**
   * Deletes an emotion and redirects back to the emotion list.
   *
   * @param id emotion ID
   * @param redirectAttributes redirect flash attributes
   * @return redirect URL to the emotion list page
   */
  @PostMapping(EMOTION_PATH + "/delete")
  public String deleteEmotion(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
    var status = api.deleteEmotion(id);
    redirectAttributes.addFlashAttribute("status", status);
    return "redirect:" + BASE_PATH + EMOTION_PATH + "/all";
  }

  private EmotionRequest newEmotionRequest() {
    EmotionRequest emotionRequest = new EmotionRequest();
    emotionRequest.setClient(new ClientRequest());
    return emotionRequest;
  }

  private void ensureClient(EmotionRequest req) {
    if (req.getClient() == null) {
      req.setClient(new ClientRequest());
    }
  }
}
