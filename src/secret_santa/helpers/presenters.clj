(ns secret-santa.helpers.presenters
  (:require [markdown.core :as md]))

(defn md->html [text]
  (md/md-to-html-string text))
