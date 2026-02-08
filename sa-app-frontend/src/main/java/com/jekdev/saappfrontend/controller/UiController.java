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

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(value = UiController.BASE_PATH)
public class UiController {

  public static final String BASE_PATH = "/ui";

  public static final String CLIENT_PATH = "/client";
  public static final String EMOTION_PATH = "/emotions";

  private final SentimentApiService api;

  @GetMapping(value = CLIENT_PATH)
  public String getBasePath(Model model) {
    model.addAttribute("clientRequest", new ClientRequest());
    return "index";
  }

  @PostMapping("/new/client")
  public String createClient(@Valid @ModelAttribute("clientRequest") ClientRequest req, BindingResult br, Model model) {
    if (br.hasErrors()) return "index";

    var status = api.createUser(req);
    log.info("API /api/client/create status = {}", status);

    model.addAttribute("status", status);
    return "index";
  }

  @GetMapping(CLIENT_PATH + "/all")
  public String getAllClient(Model model) {
    model.addAttribute("clients", api.getAllClient());
    return "listUser";
  }

  @GetMapping(CLIENT_PATH + "/find")
  public String getOneClient(@RequestParam("id") Long id, Model model) {
    model.addAttribute("client", api.findSingleClient(id));
    return "singleUser";
  }

  @GetMapping(EMOTION_PATH)
  public String getCreateEmotionPage(Model model) {
    if (!model.containsAttribute("emotionRequest")) {
      model.addAttribute("emotionRequest", newEmotionRequest());
    }
    return "createSentiment";
  }

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

  @GetMapping(EMOTION_PATH + "/all")
  public String getAllEmotion(Model model) {
    model.addAttribute("emotions", api.getAllEmotions());
    return "listEmotion";
  }

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
