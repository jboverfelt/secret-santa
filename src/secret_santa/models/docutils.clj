(ns secret-santa.models.docutils
  (:require [secret-santa.models.timeutils :as tutils]))

(defn setup-new-document [document oid]
  (-> document
      (assoc :_id oid)
      (tutils/add-created-and-updated)))
