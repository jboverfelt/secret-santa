(ns secret-santa.helpers.presenters
  (:require [clojure.string :as str]))

(defn text->html [text]
  (if text
    (str/replace text "\r\n" "<br />")
    ""))