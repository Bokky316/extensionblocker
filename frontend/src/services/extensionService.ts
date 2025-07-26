import { api } from './api'
import type { ApiResponse } from './api'
import type { FixedExtension, CustomExtension } from '../types'

// 🔹 1. 고정 확장자 조회
export const getFixedExtensions = async (): Promise<FixedExtension[]> => {
  const res = await api.get<ApiResponse<FixedExtension[]>>('/fixed')
  return res.data.data
}

// 🔹 2. 고정 확장자 체크 상태 변경
export const toggleFixedExtension = async (id: number): Promise<FixedExtension> => {
  const res = await api.put<ApiResponse<FixedExtension>>(`/fixed/${id}`)
  return res.data.data
}

// 🔹 3. 커스텀 확장자 조회
export const getCustomExtensions = async (): Promise<CustomExtension[]> => {
  const res = await api.get<ApiResponse<CustomExtension[]>>('/custom')
  return res.data.data
}

// 🔹 4. 커스텀 확장자 추가
export const addCustomExtension = async (name: string): Promise<CustomExtension> => {
  const res = await api.post<ApiResponse<CustomExtension>>('/custom', { name })
  return res.data.data
}

// 🔹 5. 커스텀 확장자 삭제
export const deleteCustomExtension = async (id: number): Promise<void> => {
  await api.delete(`/custom/${id}`)
}
