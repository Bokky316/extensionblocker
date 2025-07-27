import axios from 'axios'
import { api } from './api' // 사용 안 하면 제거 가능
import type { ApiResponse } from './api'
import type { FixedExtension, CustomExtension } from '@/types'

const API = import.meta.env.VITE_API_BASE_URL

/** 고정 확장자 목록 조회 */
export const getFixedExtensions = async (): Promise<FixedExtension[]> => {
  const res = await axios.get(`${API}/api/fixed`)
  return res.data.data
}

/** 고정 확장자 체크 상태 토글 */
export const toggleFixedExtension = async (id: number): Promise<number> => {
  const res = await axios.put(`${API}/api/fixed/${id}`)
  return id
}

/** 커스텀 확장자 목록 조회 */
export const getCustomExtensions = async (): Promise<CustomExtension[]> => {
  const res = await axios.get(`${API}/api/extensions/custom`)
  return res.data.data
}

/** 커스텀 확장자 등록 */
export const addCustomExtension = async (name: string): Promise<CustomExtension> => {
  const res = await axios.post(`${API}/api/extensions/custom`, { name })
  return res.data.data
}

/** 커스텀 확장자 삭제 */
export const deleteCustomExtension = async (id: number): Promise<void> => {
  await axios.delete(`${API}/api/extensions/custom/${id}`)
}
