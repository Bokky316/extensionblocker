import { useEffect, useState } from 'react'
import type { CustomExtension } from '../types'
import {
  getCustomExtensions,
  addCustomExtension,
  deleteCustomExtension,
} from '../services/extensionService'

export const useCustomExtensions = () => {
  const [customList, setCustomList] = useState<CustomExtension[]>([])
  const [loading, setLoading] = useState(false)

  useEffect(() => {
    fetchList()
  }, [])

  const fetchList = async () => {
    setLoading(true)
    try {
      const data = await getCustomExtensions()
      setCustomList(data)
    } finally {
      setLoading(false)
    }
  }

  const add = async (name: string) => {
    await addCustomExtension(name)
    fetchList()
  }

  const remove = async (id: number) => {
    await deleteCustomExtension(id)
    fetchList()
  }

  return { customList, loading, add, remove }
}
