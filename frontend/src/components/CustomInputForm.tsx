import { useState } from 'react'
import { useAppSelector } from '@/hooks/useAppSelector'
import { useCustomExtensions } from '@/hooks/useCustomExtensions'
import { FormInput } from '@/components/common/FormInput'
import { FormErrorMessage } from '@/components/common/FormErrorMessage'
import type { RootState } from '@/store'
import type { FixedExtension, CustomExtension } from '@/types'

export const CustomInputForm = () => {
  const [input, setInput] = useState('')
  const [error, setError] = useState('')
  const [touched, setTouched] = useState(false)

  const fixedList: FixedExtension[] = useAppSelector((state: RootState) => state.fixedExtensions.list ?? [])
  const customList: CustomExtension[] = useAppSelector((state: RootState) => state.customExtensions.list ?? [])
  const { addExtension } = useCustomExtensions()

  const validateInput = (value: string): string => {
    const trimmed = value.trim()

    if (trimmed.length > 20) return '20자 이하로 입력해주세요.'
    if (!/^[a-zA-Z]*$/.test(trimmed)) return '확장자는 영문만 입력 가능합니다.'
    if (/\s/.test(value)) return '공백은 입력할 수 없습니다.'

    const lower = trimmed.toLowerCase()
    if (fixedList.some((ext) => ext?.name?.toLowerCase() === lower))
      return '고정 확장자에 이미 존재합니다.'
    if (customList.some((ext) => ext?.name?.toLowerCase() === lower))
      return '이미 등록된 커스텀 확장자입니다.'

    return ''
  }

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value
    setInput(value)
    if (touched) {
      setError(validateInput(value))
    }
  }

  const handleBlur = () => {
    setTouched(true)
    setError(validateInput(input))
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    const trimmed = input.trim()
    const lower = trimmed.toLowerCase()

    const localError = validateInput(trimmed)
    if (localError) {
      setError(localError)
      setTouched(true)
      return
    }

    try {
      await addExtension(lower)
      setInput('')
      setError('')
      setTouched(false)
    } catch (err: any) {
      const code = err?.code
      const message = err?.message
      const details = err?.data

      if (message?.includes('고정 확장자')) {
        setError('고정 확장자에 이미 존재합니다.')
      } else if (message?.includes('커스텀')) {
        setError('이미 등록된 커스텀 확장자입니다.')
      } else if (code === 4002) {
        setError('최대 200개까지 등록할 수 있습니다.')
      } else if (code === 4000 && details) {
        const field = Object.keys(details)[0]
        const msg = details[field] ?? ''
        if (field === 'name') {
          if (msg.includes('영문')) setError('확장자는 영문만 입력 가능합니다.')
          else if (msg.includes('공백')) setError('공백은 입력할 수 없습니다.')
          else if (msg.includes('20자')) setError('20자 이하로 입력해주세요.')
          else setError('확장자 입력값이 올바르지 않습니다.')
        } else {
          setError('입력값이 유효하지 않습니다.')
        }
      } else {
        setError('등록 중 알 수 없는 오류가 발생했습니다.')
      }
    }
  }

  return (
    <form onSubmit={handleSubmit} className="flex flex-col gap-2 mt-4 w-60">
    <h2 className="text-xl font-bold mb-2">커스텀 확장자</h2>
      <div className="flex gap-2">
        <FormInput
          value={input}
          onChange={handleInputChange}
          onBlur={handleBlur}
          placeholder="확장자 입력 (예: conf)"
          error={error}
          maxLength={20}
        />
        <button type="submit" className="bg-blue-500 text-white px-3 py-1 rounded">
          +
        </button>
      </div>
      <FormErrorMessage message={error} />
    </form>
  )
}
