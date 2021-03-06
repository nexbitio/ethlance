(ns ethlance.ui.page.sign-up
  (:require
   [re-frame.core :as re]
   [taoensso.timbre :as log]
   [cuerdas.core :as str]
   [district.ui.component.page :refer [page]]

   ;; Re-frame Subscriptions
   [district.ui.router.subs :as router.subs]

   [ethlance.shared.enumeration.currency-type :as enum.currency]
   [ethlance.shared.constants :as constants]

   ;; Ethlance Components
   [ethlance.ui.component.currency-input :refer [c-currency-input]]
   [ethlance.ui.component.email-input :refer [c-email-input]]
   [ethlance.ui.component.inline-svg :refer [c-inline-svg]]
   [ethlance.ui.component.main-layout :refer [c-main-layout]]
   [ethlance.ui.component.radio-select :refer [c-radio-select c-radio-secondary-element]]
   [ethlance.ui.component.rating :refer [c-rating]]
   [ethlance.ui.component.search-input :refer [c-chip-search-input]]
   [ethlance.ui.component.tabular-layout :refer [c-tabular-layout]]
   [ethlance.ui.component.tag :refer [c-tag c-tag-label]]
   [ethlance.ui.component.text-input :refer [c-text-input]]
   [ethlance.ui.component.button :refer [c-button c-button-icon-label]]
   [ethlance.ui.component.checkbox :refer [c-labeled-checkbox]]
   [ethlance.ui.component.textarea-input :refer [c-textarea-input]]
   [ethlance.ui.component.select-input :refer [c-select-input]]
   [ethlance.ui.component.icon :refer [c-icon]]))


(defn- c-upload-image []
  []
  [:div.upload-image
   [c-icon {:name :ic-upload :color :dark-blue :inline? false}]
   [:span "Upload Image"]])


(defn c-candidate-sign-up
  []
  (let [*full-name (re/subscribe [:page.sign-up/candidate-full-name])
        *professional-title (re/subscribe [:page.sign-up/candidate-professional-title])
        *email (re/subscribe [:page.sign-up/candidate-email])
        *hourly-rate (re/subscribe [:page.sign-up/candidate-hourly-rate])
        *github-key (re/subscribe [:page.sign-up/candidate-github-key])
        *linkedin-key (re/subscribe [:page.sign-up/candidate-linkedin-key])
        *languages (re/subscribe [:page.sign-up/candidate-languages])
        *categories (re/subscribe [:page.sign-up/candidate-categories])
        *skills (re/subscribe [:page.sign-up/candidate-skills])
        *biography (re/subscribe [:page.sign-up/candidate-biography])
        *country (re/subscribe [:page.sign-up/candidate-country])
        *ready-for-hire? (re/subscribe [:page.sign-up/candidate-ready-for-hire?])]
    (fn []
      [:div.candidate-sign-up
       [:div.form-container
        [:div.label "Sign Up"]
        [:div.first-forms
         [:div.form-image
          [c-upload-image]]
         [:div.form-name
          [c-text-input
           {:placeholder "Name"
            :value @*full-name
            :on-change #(re/dispatch [:page.sign-up/set-candidate-full-name %])}]]
         [:div.form-email
          [c-email-input
           {:placeholder "Email"
            :value @*email
            :on-change #(re/dispatch [:page.sign-up/set-candidate-email %])}]]
         [:div.form-professional-title
          [c-text-input
           {:placeholder "Professional Title"
            :value @*professional-title
            :on-change #(re/dispatch [:page.sign-up/set-candidate-professional-title %])}]]
         [:div.form-hourly-rate
          [c-currency-input
           {:placeholder "Hourly Rate"
            :color :primary
            :min 0
            :value @*hourly-rate
            :on-change #(re/dispatch [:page.sign-up/set-candidate-hourly-rate %])}]]
         [:div.form-country
          [c-select-input
           {:label "Select Country"
            :selections constants/countries
            :selection @*country
            :on-select #(re/dispatch [:page.sign-up/set-candidate-country %])
            :search-bar? true
            :default-search-text "Search Countries"}]]
         [:div.form-connect-github
          [c-button
           {:size :large}
           [c-button-icon-label {:icon-name :github :label-text "Connect Github" :inline? false}]]]
         [:div.form-connect-linkedin
          [c-button
           {:size :large}
           [c-button-icon-label {:icon-name :linkedin :label-text "Connect LinkedIn" :inline? false}]]]]
        [:div.second-forms
         [:div.label [:h2 "Languages You Speak"]]
         [c-chip-search-input
          {:search-icon? false
           :placeholder ""
           :auto-suggestion-listing constants/languages
           :allow-custom-chips? false
           :chip-listing @*languages
           :on-chip-listing-change #(re/dispatch [:page.sign-up/set-candidate-languages %])}]
         
         [:div.label [:h2 "Categories You Are Interested In"]]
         [c-chip-search-input
          {:search-icon? false
           :placeholder ""
           :auto-suggestion-listing (sort constants/categories)
           :allow-custom-chips? false
           :chip-listing @*categories
           :on-chip-listing-change #(re/dispatch [:page.sign-up/set-candidate-categories %])
           :display-listing-on-focus? true}]

         [:div.label [:h2 "Your Skills "] [:i "(Choose at least one skill)"]]
         [c-chip-search-input
          {:search-icon? false
           :placeholder ""
           :allow-custom-chips? false
           :auto-suggestion-listing constants/skills
           :chip-listing @*skills
           :on-chip-listing-change #(re/dispatch [:page.sign-up/set-candidate-skills %])}]

         [:div.label [:h2 "Your Biography"]]
         [c-textarea-input
          {:placeholder ""
           :value @*biography
           :on-change #(re/dispatch [:page.sign-up/set-candidate-biography %])}]
         [c-labeled-checkbox
          {:id "form-for-hire"
           :label "I'm available for hire"
           :checked? @*ready-for-hire?
           :on-change #(re/dispatch [:page.sign-up/set-candidate-ready-for-hire? %])}]]]
       
       [:div.form-submit
        [:span "Create"]
        [c-icon {:name :ic-arrow-right :size :smaller}]]])))


(defn c-employer-sign-up []
  (let [*full-name (re/subscribe [:page.sign-up/employer-full-name])
        *professional-title (re/subscribe [:page.sign-up/employer-professional-title])
        *email (re/subscribe [:page.sign-up/employer-email])
        *github-key (re/subscribe [:page.sign-up/employer-github-key])
        *linkedin-key (re/subscribe [:page.sign-up/employer-linkedin-key])
        *languages (re/subscribe [:page.sign-up/employer-languages])
        *biography (re/subscribe [:page.sign-up/employer-biography])
        *country (re/subscribe [:page.sign-up/employer-country])]
    (fn []
      [:div.employer-sign-up
       [:div.form-container
        [:div.label "Sign Up"]
        [:div.first-forms
         [:div.form-image
          [c-upload-image]]
         [:div.form-name
          [c-text-input
           {:placeholder "Name"
            :value @*full-name
            :on-change #(re/dispatch [:page.sign-up/set-employer-full-name %])}]]
         [:div.form-email
          [c-email-input
           {:placeholder "Email"
            :value @*email
            :on-change #(re/dispatch [:page.sign-up/set-employer-email %])}]]
         [:div.form-professional-title
          [c-text-input
           {:placeholder "Professional Title"
            :value @*professional-title
            :on-change #(re/dispatch [:page.sign-up/set-employer-professional-title %])}]]
         [:div.form-country
          [c-select-input
           {:label "Select Country"
            :selections constants/countries
            :selection @*country
            :on-select #(re/dispatch [:page.sign-up/set-employer-country %])
            :search-bar? true
            :default-search-text "Search Countries"}]]
         [:div.form-connect-github
          [c-button
           {:size :large}
           [c-button-icon-label {:icon-name :github :label-text "Connect Github" :inline? false}]]]
         [:div.form-connect-linkedin
          [c-button
           {:size :large}
           [c-button-icon-label {:icon-name :linkedin :label-text "Connect LinkedIn" :inline? false}]]]]

        [:div.second-forms
         [:div.label [:h2 "Languages You Speak"]]
         [c-chip-search-input
          {:search-icon? false
           :placeholder ""
           :auto-suggestion-listing constants/languages
           :allow-custom-chips? false
           :chip-listing @*languages
           :on-chip-listing-change #(re/dispatch [:page.sign-up/set-employer-languages %])}]

         [:div.label [:h2 "Your Biography"]]
         [c-textarea-input
          {:placeholder ""
           :value @*biography
           :on-change #(re/dispatch [:page.sign-up/set-employer-biography %])}]]]

       [:div.form-submit
        [:span "Create"]
        [c-icon {:name :ic-arrow-right :size :smaller}]]])))


(defn c-arbiter-sign-up []
  (let [*full-name (re/subscribe [:page.sign-up/arbiter-full-name])
        *professional-title (re/subscribe [:page.sign-up/arbiter-professional-title])
        *fixed-rate-per-dispute (re/subscribe [:page.sign-up/arbiter-fixed-rate-per-dispute])
        *email (re/subscribe [:page.sign-up/arbiter-email])
        *github-key (re/subscribe [:page.sign-up/arbiter-github-key])
        *linkedin-key (re/subscribe [:page.sign-up/arbiter-linkedin-key])
        *languages (re/subscribe [:page.sign-up/arbiter-languages])
        *biography (re/subscribe [:page.sign-up/arbiter-biography])
        *country (re/subscribe [:page.sign-up/arbiter-country])]
    (fn []
      [:div.arbiter-sign-up
       [:div.form-container
        [:div.label "Sign Up"]
        [:div.first-forms
         [:div.form-image
          [c-upload-image]]
         [:div.form-name
          [c-text-input
           {:placeholder "Name"
            :value @*full-name
            :on-change #(re/dispatch [:page.sign-up/set-arbiter-full-name %])}]]
         [:div.form-email
          [c-email-input
           {:placeholder "Email"
            :value @*email
            :on-change #(re/dispatch [:page.sign-up/set-arbiter-email %])}]]
         [:div.form-professional-title
          [c-text-input
           {:placeholder "Professional Title"
            :value @*professional-title
            :on-change #(re/dispatch [:page.sign-up/set-arbiter-professional-title %])}]]
         [:div.form-country
          [c-select-input
           {:label "Select Country"
            :selections constants/countries
            :selection @*country
            :on-select #(re/dispatch [:page.sign-up/set-arbiter-country %])
            :search-bar? true
            :default-search-text "Search Countries"}]]
         [:div.form-hourly-rate
          [c-currency-input
           {:placeholder "Fixed Rate Per A Dispute" :color :primary
            :value @*fixed-rate-per-dispute
            :on-change #(re/dispatch [:page.sign-up/set-arbiter-fixed-rate-per-dispute %])}]]
         [:div.form-connect-github
          [c-button
           {:size :large}
           [c-button-icon-label {:icon-name :github :label-text "Connect Github" :inline? false}]]]
         [:div.form-connect-linkedin
          [c-button
           {:size :large}
           [c-button-icon-label {:icon-name :linkedin :label-text "Connect LinkedIn" :inline? false}]]]]

        [:div.second-forms
         [:div.label [:h2 "Languages You Speak"]]
         [c-chip-search-input
          {:search-icon? false
           :placeholder ""
           :auto-suggestion-listing constants/languages
           :allow-custom-chips? false
           :on-chip-listing-change (fn [languages] (log/info "Languages: " languages))}]

         [:div.label [:h2 "Your Biography"]]
         [c-textarea-input {:placeholder ""}]]]

       [:div.form-submit
        [:span "Create"]
        [c-icon {:name :ic-arrow-right :size :smaller}]]])))


(defmethod page :route.me/sign-up []
  (let [*active-page-query (re/subscribe [::router.subs/active-page-query])]
    (fn []
      (let [active-tab-index
            (case (-> @*active-page-query :tab str/lower str/keyword)
              :candidate 0
              :employer 1
              :arbiter 2
              0)]
        [c-main-layout {:container-opts {:class :sign-up-main-container}}
         [c-tabular-layout
          {:key "sign-up-tabular-layout"
           :default-tab active-tab-index}
          
          {:label "Candidate"}
          [c-candidate-sign-up]
          
          {:label "Employer"}
          [c-employer-sign-up]
          
          {:label "Arbiter"}
          [c-arbiter-sign-up]]]))))
