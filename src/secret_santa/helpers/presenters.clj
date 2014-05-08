(ns secret-santa.helpers.presenters
  (:require [autoclave.core :as sanitize]
            [markdown.core :as md]))

(defn md->html [text]
  (md/md-to-html-string (sanitize/html-sanitize text)))
