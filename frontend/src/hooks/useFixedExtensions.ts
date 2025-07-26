import { useEffect, useState } from 'react'
import type { FixedExtension } from '../types'
import {
  getFixedExtensions,
  toggleFixedExtension,
} from '../services/extensionService'

export const useFixedExtensions = () => {
  const [fixedList, setFixedList] = useState<FixedExtension[]>([])
  const [loading, setLoading] = useState(false)

  useEffect(() => {
    fetchList()
  }, [])

  const fetchList = async () => {
    setLoading(true)
    try {
      const data = await getFixedExtensions()
      setFixedList(data)
    } finally {
      setLoading(false)
    }
  }

  const toggle = async (id: number) => {
    await toggleFixedExtension(id)
    fetchList()
  }

  return { fixedList, loading, toggle }
}

