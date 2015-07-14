(ns uxbox.db
  (:require [reagent.core :as reagent :refer [atom]]))

(defonce a-project-id (random-uuid))
(defonce another-project-id (random-uuid))

(def initial-state {:location [:login]
                    :lightbox nil
                    :open-setting-boxes #{:tools :layers}
                    :user {:fullname "Michael Buchannon"
                           :avatar "/images/avatar.jpg"}
                    :activity [
                      {:author {:name "Other user" :avatar "../../images/avatar.jpg" }
                       :uuid (random-uuid)
                       :project {:uuid  a-project-id :name "Design of UXBox"}
                       :datetime (js/Date. 2015 6 13 17 15)
                       :event {:type "create new page" :name "Login"}}
                      {:author {:name "Michael Buchannon" :avatar "../../images/avatar.jpg"}
                       :uuid (random-uuid)
                       :project {:uuid  another-project-id :name "Wireframes Taiga Tribe"}
                       :datetime (js/Date. 2015 6 12 17 00)
                       :event {:type "create new page" :name "Login"}}
                      {:author {:name "Michael Buchannon" :avatar "../../images/avatar.jpg"}
                       :uuid (random-uuid)
                       :project {:uuid  another-project-id :name "Wireframes Taiga Tribe"}
                       :datetime (js/Date. 2015 6 11 17 00)
                       :event {:type "create new page" :name "Login"}}
                      {:author {:name "Michael Buchannon" :avatar "../../images/avatar.jpg"}
                       :uuid (random-uuid)
                       :project {:uuid  another-project-id :name "Wireframes Taiga Tribe"}
                       :datetime (js/Date. 2015 6 11 17 00)
                       :event {:type "create new page" :name "Login"}}
                      {:author {:name "Michael Buchannon" :avatar "../../images/avatar.jpg"}
                       :uuid (random-uuid)
                       :project {:uuid  another-project-id :name "Wireframes Taiga Tribe"}
                       :datetime (js/Date. 2015 6 10 17 00)
                       :event {:type "create new page" :name "Login"}}]
                    :project-sort-order :name
                    :project-orderings {
                      :name "name"
                      :last-update "date updated"
                      :created "date created"
                    }
                    :project-layouts {
                      :mobile {:name "Mobile" :width 320 :height 480}
                      :tablet {:name "Tablet" :width 1024 :height 768}
                      :notebook {:name "Notebook" :width 1366 :height 768}
                      :desktop {:name "Desktop" :width 1920 :height 1080}
                    }
                    :projects-list {}
                    :new-project-defaults {
                      :name ""
                    }
                    :workspace {:selected-tool nil
                                :selected-groups #{}}

                    ;; Rectangles: http://www.w3.org/TR/SVG/shapes.html#RectElement
                    ;; Lines: http://www.w3.org/TR/SVG/shapes.html#LineElement
                    ;; Style properties http://www.w3.org/TR/SVG/propidx.html
                    :current-catalog :material-design-actions

                    :page nil})

                     :selected nil ;; UUID of the current selected shape

(defonce app-state (atom initial-state))
