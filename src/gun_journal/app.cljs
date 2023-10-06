(ns gun-journal.app
  (:require [reagent.dom :as rd]
            [datascript.core :as d]))

(def db (d/create-conn {}))

(defn create-bullet []
  (d/transact! db [{:db/id -1
                    :text "Hello World"}]))

(defn get-bullet []
  (d/q '[:find ?text
         :where [?e :text ?text]]
       @db))

(defn create-random-bullets [count]
  ;; TODO: add different types of bullet structures (one parent, ledder-like parents...)
  (doseq [i (range count)]
    (create-bullet)))

(defn buttons-component []
  [:div
   [:div
    "Create random bullets: "
    [:button {:on-click #(create-random-bullets 100)} "100"]
    [:button {:on-click #(create-random-bullets 1000)} "1k"]
    [:button {:on-click #(create-random-bullets 10000)} "10k"]
    [:button {:on-click #(create-random-bullets 100000)} "100k"]
    [:button {:on-click #(create-random-bullets 1000000)} "1kk"]]

   [:div
    "Create bullets to the daily bullet: "
    [:button "create daily bullet"]
    [:button "100"]
    [:button "1k"]
    [:button "10k"]
    [:button "100k"]
    [:button "1kk"]]

   [:div
    "Get random bullets: "
    [:button {:on-click #(println (get-bullet))} "100"]
    [:button "1k"]
    [:button "10k"]
    [:button "100k"]
    [:button "1kk"]]

   [:div
    "Get random daily bullets: "
    [:button "100"]
    [:button "1k"]
    [:button "10k"]
    [:button "100k"]
    [:button "1kk"]]])

(defn app-component []
  [:<>
   [:h1 "DataScript Experiments"]
   [buttons-component]])

(defn main []
  (rd/render
    [app-component]
    (.getElementById js/document "app")))
