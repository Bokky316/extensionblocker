import axios from 'axios'
import type { FixedExtension, CustomExtension } from '@/types'

const API = import.meta.env.VITE_API_BASE_URL

export const getFixedExtensions = async (): Promise<FixedExtension[]> => {
  const res = await axios.get(`${API}/api/fixed`)
  return res.data.data
}

export const toggleFixedExtension = async (id: number): Promise<number> => {
  await axios.put(`${API}/api/fixed/${id}`)
  return id
}

export const getCustomExtensions = async (): Promise<CustomExtension[]> => {
  const res = await axios.get(`${API}/api/extensions/custom`)
  return res.data.data
}

export const addCustomExtension = async (name: string): Promise<CustomExtension> => {
  const res = await axios.post(`${API}/api/extensions/custom`, { name })
  return res.data.data
}

export const deleteCustomExtension = async (id: number): Promise<void> => {
  await axios.delete(`${API}/api/extensions/custom/${id}`)
}
