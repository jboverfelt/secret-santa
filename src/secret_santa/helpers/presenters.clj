(ns secret-santa.helpers.presenters
  (:require [markdown.core :as md]))

(defn remove-script [text state]
  [(clojure.string/replace text #"<script>" "") state])

(defn remove-img [text state]
  [(clojure.string/replace text #"img" "") state])

(defn md->html [text]
  (md/md-to-html-string text :custom-transformers [remove-script remove-img]))

