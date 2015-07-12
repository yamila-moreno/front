(ns uxbox.forms
  (:require [uxbox.projects.actions :refer [create-project]]
            [uxbox.pubsub :as pubsub]
            [goog.events :as events]
            [cuerdas.core :refer [trim]])
  (:import goog.events.EventType))

(defn close-lightbox
  []
  (pubsub/publish! [:close-lightbox]))

(pubsub/register-handler
 :close-lightbox
 (fn [state _]
   (assoc state :lightbox nil :new-project-name "")))

(defn lightbox*
  [db]
  (let [tag (if (:lightbox @db) ;; TODO: select lightbox form depending on this value
              :div.lightbox
              :div.lightbox.hide)
        project-name (:new-project-name @db)]
    [tag
     [:div.lightbox-body
      [:h3 "New project"]
      [:input#project-name.input-text
       {:placeholder "New project name"
        :type "text"
        :value project-name
        :on-change #(swap! db assoc :new-project-name (.-value (.-target %)))}]
      (when (not (empty? (trim project-name)))
        [:input#project-btn.btn-primary
          {:value "Go go go!"
           :type "button"
           :on-click #(do
                        (create-project (trim project-name))
                        (close-lightbox))}])
      [:a.close
       {:href "#"
        :on-click #(close-lightbox)}
       [:svg#svg2
        {:sodipodi:docname "plus.svg",
         :version "1.1",
         :viewBox "0 0 500 500",
         :height "500",
         :width "500"}
        [:defs#defs4]
        [:sodipodi:namedview#base
         {:units "px",
          :showgrid "false",
          :borderopacity "1.0",
          :bordercolor "#666666",
          :pagecolor "#ffffff"}]
        [:metadata#metadata7
         [:rdf:RDF
          [:cc:Work
           {:rdf:about ""}
           [:dc:format "\n          image/svg+xml\n        "]
           [:dc:type
            {:rdf:resource "http://purl.org/dc/dcmitype/StillImage"}]]]]
        [:g#layer1
         {:transform "translate(0,-552.36216)"}
         [:path#path4149
          {:d
           "m254.83206 1051.8787c-2.65759 0.6446-7.00641 0.6446-9.66407 0-5.77272-1.4-13.14681-8.2641-14.84551-13.8188-0.64086-2.0956-1.13697-50.68291-1.10251-107.97192 0.0345-57.28903-0.56181-104.78648-1.32503-105.5499-0.76322-0.76342-48.24831-1.35984-105.52242-1.32537-57.274094 0.0345-105.848829-0.46179-107.943842-1.10281-2.095015-0.641-5.7968829-3.15684-8.2263775-5.59077-8.2697477-8.28472-8.2697131-19.89906 0-28.18382 2.4299131-2.4335 6.1313615-4.94977 8.2263775-5.59077 2.095016-0.64101 50.66975-1.13727 107.943842-1.1028 57.27411 0.0345 104.7592-0.56195 105.52242-1.32538 0.76322-0.76342 1.35948-48.26087 1.32503-105.54989-0.0345-57.28901 0.46168-105.87639 1.10252-107.97196 0.64083-2.09555 3.15602-5.79839 5.58931-8.22852 8.28279-8.27168 19.89389-8.27186 28.17649 0 2.43287 2.43055 4.94848 6.13297 5.58931 8.22852 0.64085 2.09557 1.13698 50.68295 1.10252 107.97196-0.0345 57.28901 0.56181 104.78647 1.32503 105.54989 0.76322 0.76343 48.24831 1.35984 105.52241 1.32539 57.2741-0.0345 105.84882 0.46176 107.94383 1.10278 2.09504 0.64101 5.79694 3.1569 8.22638 5.59077 8.26952 8.28494 8.26976 19.8991 0 28.18383-2.43138 2.43202-6.13137 4.94974-8.22634 5.5908-2.09503 0.64098-50.66977 1.13725-107.94386 1.10278-57.27411-0.0345-104.7592 0.56195-105.52242 1.32537-0.76322 0.76342-1.35948 48.26087-1.32504 105.54989 0.0345 57.28902-0.46166 105.87643-1.10248 107.97203-1.69867 5.5546-9.07281 12.4187-14.84557 13.8187z"}]]]]]]))

(defn dismiss-lightbox
  [e]
  (when (= (.-keyCode e) 27)
    (close-lightbox)))

;; NOTE: alter-meta! does not work on vars http://dev.clojure.org/jira/browse/CLJS-1248
(def lightbox
  (with-meta lightbox* {:component-did-mount #(events/listen js/document EventType.KEYUP dismiss-lightbox)
                        :component-will-unmount #(events/unlisten js/document EventType.KEYUP dismiss-lightbox)}))