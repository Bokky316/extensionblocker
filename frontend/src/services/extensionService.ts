import axios from 'axios'
import type { FixedExtension, CustomExtension } from '@/types'

const API = import.meta.env.VITE_API_BASE_URL

// 고정 확장자 목록 조회
export const getFixedExtensions = async (): Promise<FixedExtension[]> => {
  const res = await axios.get(`${API}/fixed`)
  const data = res.data.data ?? res.data
  if (!Array.isArray(data)) throw new Error('고정 확장자 응답 형식 오류')
  return data
}

// 고정 확장자 체크 상태 토글 (data가 null이어도 허용)
export const toggleFixedExtension = async (id: number): Promise<FixedExtension | null> => {
  const res = await axios.put(`${API}/fixed/${id}`)
  const data = res.data.data ?? null

  if (data === null) {
    console.warn('[toggleFixedExtension] 응답 data가 null입니다.')
    return null
  }

  return data
}

// 커스텀 확장자 목록 조회
export const getCustomExtensions = async (): Promise<CustomExtension[]> => {
  const res = await axios.get(`${API}/extensions/custom`)
  const data = res.data.data ?? res.data
  if (!Array.isArray(data)) throw new Error('커스텀 확장자 응답 형식 오류')
  return data
}

// 커스텀 확장자 등록
export const addCustomExtension = async (name: string): Promise<CustomExtension> => {
  const res = await axios.post(`${API}/extensions/custom`, { name })
  const data = res.data.data ?? res.data
  if (!data || typeof data.id !== 'number') throw new Error('등록 응답 이상')
  return data
}

// 커스텀 확장자 삭제
export const deleteCustomExtension = async (id: number): Promise<void> => {
  await axios.delete(`${API}/extensions/custom/${id}`)
}
